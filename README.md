# easyhttps

  This is **_easy_** cdoe to show how to use java .net class to make HTTPS GEt/POST call.

### classes
```
HttpsClient:  provides GET/POST method to access HTTPS server
```
## Build

- mvn install

## Notes about HTTPS
If you are getting HTTPs handshake error from JavaVM, one way to solve it is by adding certificate to JDK. From browser like firefox to export
certificate to local directory (eg. ~/Documents), then run keytool to import certficate to your JDK.
```
cd <JDK-dectory>
keytool -importcert -file ~/Documents/<export-file-from-firefox>.crt -keystore ./jre/lib/security/cacerts -storepass changeit -alias <name of export-file-from-firefox>
```




