#define LED 9

int signal = -1;

void setup() {
	pinMode(LED, OUTPUT);
	while (!Serial) {
		// Waiting to connect...
	};
	Serial.begin(9600);
}

void loop() {
	if (Serial.available() > 0) {
		signal = Serial.read();
		switch (signal) {
		// LED Controller
		case '0':
			digitalWrite(LED, LOW);
			break;
		case '1':
			digitalWrite(LED, HIGH);
			break;
		default:
			break;
		}

	}
}
