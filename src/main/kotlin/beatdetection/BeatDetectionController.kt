package beatdetection

import com.bitwig.extension.controller.ControllerExtension
import com.bitwig.extension.controller.api.*
import java.nio.ByteBuffer
import kotlin.random.Random

class BeatDetectionController(definition: BeatDetectionControllerDefinition, host: ControllerHost) :
    ControllerExtension(definition, host) {

    override fun init() {
        val host = host
        val transport = host.createTransport()
        val cursorTrack =
            host.createCursorTrack("track", "track", 0, 0, true)
        cursorTrack.subscribe()
        val cursor = cursorTrack.createCursorDevice("track", "track", 0, CursorDeviceFollowMode.FOLLOW_SELECTION)
        cursor.subscribe()

        var inputPortParameter: String? = null;

        var port = Random.nextInt(1024, 65536);
        val connection = host.createRemoteConnection("BPM Receiver", port)

        if (connection.port > -1) {
            port = connection.port
        }

        connection.setClientConnectCallback {
            it.setReceiveCallback { bytes ->
                val bpm = ByteBuffer.wrap(bytes).float;
                transport.tempo().value().raw = bpm.toDouble()
            }
        }

        cursor.addDirectParameterIdObserver { ids ->
            if (!ids.any {
                    (it?.split("/")?.last() == inputPortParameter?.split("/")?.last())
                }) {

                cursorTrack.isPinned.set(false)
                cursor.isPinned.set(false)
            }
        }

        cursor.addDirectParameterNameObserver(20) { id, name ->
            if (name == "DAW Port") {
                inputPortParameter = id
                cursorTrack.isPinned.set(true)
                cursor.isPinned.set(true)
                cursor.setDirectParameterValueNormalized(inputPortParameter, port, 65536)
            }
        }
    }

    override fun exit() {

    }

    override fun flush() {

    }
}
