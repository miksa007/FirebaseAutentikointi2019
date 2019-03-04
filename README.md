# FirebaseAutentikointi2019
Tutoriaali autentikoinnin lisäykseen

##Oheismateriaali ja muut huomiot
* [https://firebase.google.com/docs/auth/android/password-auth](https://firebase.google.com/docs/auth/android/password-auth)
* Repo sisältää vain muutettavia koodi -tiedostoja, joten ei ole ajettavissa suoraan
* Tässä luetellaan eri työvaiheet seuraten dokumentaatiota


##Osa 1
* Aloitetaan uusi empty project Android Studiolla
* Lisätään projekki Firebaseen, vanhojen oppien mukaan tai seuraten ohjetta:[https://firebase.google.com/docs/android/setup](https://firebase.google.com/docs/android/setup) 
* Lisätään google-services.json tiedosto mukaan android studio -projektiin
* Build.gradle -tiedostojen muokkaus (Firebase ohjeen mukaan)
* Project-level build.gradle (<project>/build.gradle):
```python
    classpath 'com.google.gms:google-services:4.0.1' 
```
* App-level build.gradle (<project>/<app-module>/build.gradle):
```python 
    implementation 'com.google.firebase:firebase-core:16.0.1'
    implementation 'com.firebaseui:firebase-ui-auth:4.3.1'
    ...
    apply plugin: 'com.google.gms.google-services'
```
* Huomioi yllä: implementation 'com.firebaseui:firebase-ui-auth:4.3.1'
* Lisää komponentit activity_main.xml tiedostoon (3 nappulaa).
* seuraillaan oheismateriaalin vaiheita ja lisäillään koodia MainActivity.java tiedostoon.
* Kaikki //Osa 1 -kommentilla mukaan omaan sovellukseen
* **Valmis testaukseen**
* LOG IN -nappula kysyy sähköpostiosoitteen, nimen ja salasanan. Ja kirjautuu Firebaseen.
* Jos kaikki meni oikein niin [https://console.firebase.google.com](https://console.firebase.google.com) Authentication ja Users. Listassa pitäisi näkyä antamasi tiedot...
* Jos ei näy - niin LogCat:stä etsimään virhettä.