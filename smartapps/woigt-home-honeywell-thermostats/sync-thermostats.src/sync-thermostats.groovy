/**
 *  SyncThermostats
 *
 *  Copyright 2019 Murilo Woigt
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Sync Thermostats",
    namespace: "woigt.home.honeywell.thermostats",
    author: "Murilo Woigt",
    description: "Synchronizes 2 Thermostats",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("Thermostats") {
		input "masterThermostat", "capability.thermostat", multiple: false, required: true, title: "Master"
		input "slaveThermostat", "capability.thermostat", multiple: false, required: true, title: "Slave"
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def uninstall() {
	unsubscribe()
    log.debug "Uninstalled."
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(masterThermostat, "thermostatSetpoint", setPointHandler)
    
}

// TODO: implement event handlers
def setPointHandler(evt) {
    def temp = evt.value
    slaveThermostat.setCoolingSetpoint (temp)
    log.debug "Temperature set! ${temp}"
}
