package beatdetection

import com.bitwig.extension.api.PlatformType
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList
import com.bitwig.extension.controller.ControllerExtensionDefinition
import com.bitwig.extension.controller.api.ControllerHost
import java.util.*

class BeatDetectionControllerDefinition : ControllerExtensionDefinition() {
    override fun getName(): String {
        return "Beat detection controller script"
    }

    override fun getAuthor(): String {
        return "Midi BPM Detection"
    }

    override fun getVersion(): String {
        return "0.1"
    }

    override fun getId(): UUID {
        return UUID.fromString("bb70b7dc-a900-46ea-8b50-611234df35e2")
    }

    override fun getHardwareVendor(): String {
        return "Midi BPM Detection"
    }

    override fun getHardwareModel(): String {
        return "Beat detection controller script"
    }

    override fun getRequiredAPIVersion(): Int {
        return 2
    }

    override fun getNumMidiInPorts(): Int {
        return 0
    }

    override fun getNumMidiOutPorts(): Int {
        return 0
    }

    override fun listAutoDetectionMidiPortNames(list: AutoDetectionMidiPortNamesList, platformType: PlatformType) {}
    override fun createInstance(host: ControllerHost): BeatDetectionController {
        return BeatDetectionController(this, host)
    }
}