#include<AFMotor.h>

AF_DCMotor motorWheel1(1);
AF_DCMotor motorWheel2(2);
AF_DCMotor motorJaw(3);
AF_DCMotor motorLift(4);

char btn = '5';
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.print("AT+INQ");
  motorWheel1.setSpeed(255);
  motorWheel2.setSpeed(255);
  motorLift.setSpeed(255);
  motorJaw.setSpeed(255);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available() > 0)
  {
    char data;
    data = Serial.read();
    Serial.write(Serial.read());

    switch (data)
    {
      
      case '1': //FORWARD
        motorWheel1.run(FORWARD);
        motorWheel2.run(FORWARD);  
        break;
        
      case '2': //REVERSE
          motorWheel1.run(BACKWARD);
          motorWheel2.run(BACKWARD);
        break;
        
      case '3': //RIGHT
          motorWheel1.run(FORWARD);
          motorWheel2.run(BACKWARD);
        break;
        
      case '4': //LEFT
          motorWheel1.run(BACKWARD);
          motorWheel2.run(FORWARD);
        break;
        
      case '5': //OPEN JAW
          motorJaw.run(BACKWARD);
        break;
        
      case '6': //CLOSE JAW
          motorJaw.run(FORWARD);
        break;
        
        case '7': //LIFT UP
          motorLift.run(FORWARD);
        break;
        
        case '8': //LIFT DOWN
          motorLift.run(BACKWARD);
        break;
               
      default: //If bluetooth module receives any value not listed above, both motors turn off
            motorWheel1.run(RELEASE);
            motorWheel2.run(RELEASE);
            motorLift.run(RELEASE);
            motorJaw.run(RELEASE);
    }
  }

}