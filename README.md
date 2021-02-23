# FirebaseAutentikointi2019
Tutoriaali autentikoinnin lisäykseen. **Osa1** käsittelee yhteyden luonnin ja testauksen autentikointiin ja tietokantaan. **Osa 2** käsittelee autentikoinnin tilatietojen tarkastelua.

## Oheismateriaali ja muut huomiot
* [https://firebase.google.com/docs/auth/android/password-auth](https://firebase.google.com/docs/auth/android/password-auth)
* Repo sisältää vain muutettavia koodi -tiedostoja, joten ei ole ajettavissa suoraan
* Tässä luetellaan eri työvaiheet seuraten dokumentaatiota


## Osa 1
* Aloitetaan uusi empty project Android Studiolla
* Lisätään projekki Firebaseen, vanhojen oppien mukaan tai seuraten ohjetta:[https://firebase.google.com/docs/android/setup](https://firebase.google.com/docs/android/setup) 
* Lisätään google-services.json tiedosto mukaan android studio -projektiin
* Build.gradle -tiedostojen muokkaus (Firebase ohjeen mukaan)
* Project-level build.gradle (<project>/build.gradle):

* App-level build.gradle (<project>/<app-module>/build.gradle):
```python 
    implementation platform('com.google.firebase:firebase-bom:26.5.0')
    implementation 'com.google.firebase:firebase-auth'
    implementation 'com.firebaseui:firebase-ui-auth:7.1.1'

```
* Huomioi yllä: versionumerointi automaattisesti käytettäessä firebase-bom -kirjastoa
* Huomioi gradlen loppuun  
```python
    apply plugin: 'com.google.gms.google-services'
```
* Lisää komponentit activity_main.xml tiedostoon (3 nappulaa).
* seuraillaan oheismateriaalin vaiheita ja lisäillään koodia MainActivity.java tiedostoon.
* Kaikki //Osa 1 -kommentilla mukaan omaan sovellukseen

## Firebase rules
* Pari erilaista esimerkkiä:   
* Realtime database Kaikki avoinna
```python
    {
      "rules": {
        ".read": true,
        ".write":true
      }
    }
```
* Realtime database - Vain kirjautuneet käyttäjä kirjoittaa
```python
    {
      "rules": {
        ".read": true,
        ".write":"auth != null"
      }
    }
```
* Realtime database rules -esimerkki User UID arvoista. (omat User UID arvot löytyy Firebase Authentication sivulta  tai esimerkiksi koodilla user.getUid()    )
```python
    {
      "rules": {
  	    "users": {
          "BEJaspxmfNcp0Z811V7H1voxZTu1": {
            ".write": true,
            ".read":true
          },
          "pNju3xZuN0Ze4gW0OGQew0MOR2k1": {
            ".write": true,
            ".read":true
          } 
        }   
      }
    }
```
* Cloud Firestore database rules
* Tietojen luku on mahdollinen, mutta kirjoittamiseen vaaditaan kirjautuminen
```python
    service cloud.firestore {
      match /databases/{database}/documents {
        match /{document=**} {
          allow read, write: if request.auth != null; 
        }
      }
    }
```

* **Valmis testaukseen**
* Firebase consolissa muista enabloida authentication osassa sähköpostikirjautuminen 
* LOG IN -nappula kysyy sähköpostiosoitteen, nimen ja salasanan. Ja kirjautuu Firebaseen.
* Jos kaikki meni oikein niin [https://console.firebase.google.com](https://console.firebase.google.com) Authentication ja Users. Listassa pitäisi näkyä antamasi tiedot...
* Jos ei näy - niin LogCat:stä etsimään virhettä.


## Osa 2
* Lisätään tilan tutkimiseen muutamia komponentteja
* kirjautumisenTila -nappula ja siihen liittyvät muuttujat
```java
    //osa 2
    FirebaseAuth.AuthStateListener mAuthListener;
```
* ja metodit
```java
    //osa 2
    public void kirjautumisenTila(View view) {
        Log.d(TAG, "tilakysely");
        checkCurrentUser();
    }

    //osa 2
    public void checkCurrentUser() {
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView textView = findViewById(R.id.textView);
        if (user != null) {
            textView.setText("sisällä"+user.getEmail());
            Log.d(TAG, user.getUid());
            // User is signed in
        } else {
            textView.setText("Ei kirjautunut");
            // No user is signed in
        }
        // [END check_current_user]
    }
```

* Myös onCreate() - metodiin kuuntelija tilamuutoksille
```java
        //osa2
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d(TAG, "tilamuuttui");
                if (user != null) {
                    // Sign in logic here.
                }
            }
        };
```
* Tämä dokumentti on päivitetty 2021 - koodia ei
* Tästä voi jatkaa...