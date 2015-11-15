*** Generate self-signed certificate ***
keytool -genkey -keyalg RSA -alias selfsigned -keystore keystore.jks -storepass password -validity 360 -keysize 2048

*** Sign jar ***
jarsigner.exe -keystore keystore.jks -storepass password -keypass password C:\Users\kirill\git\mydrivejavaupload\MyDriveJavaUpload\target\upload-full-min.jar selfsigned 

*** Export cert from keystore ***
keytool -exportcert -keystore keystore.jks -alias selfsigned -file selfcogned.cer