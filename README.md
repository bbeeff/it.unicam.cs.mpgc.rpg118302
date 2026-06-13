# 🎮 Dungeon Legend - RPG Game

Un'applicazione desktop di gioco di ruolo a turni sviluppata in Java 25 con JavaFX 21. 
Il giocatore crea un personaggio e affronta battaglie in un ambiente dark fantasy, con persistenza dati tramite Hibernate e H2 database.

---

## 🚀 Come eseguire il progetto

### Prerequisiti

* Java 25 (LTS)
* Gradle 9.1.0+

### Istruzioni

```bash
git clone <https://github.com/bbeeff/it.unicam.cs.mpgc.rpg118302.git>
cd it.unicam.cs.mpgc.rpg118302
```

### Build del progetto

```bash
./gradle clean build
```

### Esecuzione

```bash
./gradle run
```

---

## 🤖 Uso di strumenti di AI

È stato utilizzato **Claude** come supporto nello sviluppo. Di seguito il dettaglio:

### Utilizzato Claude per:

* **Debug e problem solving**: Risoluzione di errori FXML
* **Consulenza tecnica**: spiegazioni su concetti teorici e pratici.
* **Configurazione Gradle**: Setup dipendenze Hibernate, H2, JavaFX per macOS
* **Supporto alla configurazione dell’ambiente di sviluppo**: verifica delle dipendenze Gradle, configurazione di librerie esterne e gestione delle problematiche di compatibilità.
* **Revisione del codice**: individuazione di possibili miglioramenti, refactoring e suggerimenti.
  
### Processo di utilizzo:

* Claude ha fornito **soluzioni iniziali**, per creazioni del progetto.
* Gli errori sono stati debuggati insieme, con iterazioni fino alla soluzione funzionante
  
* L’Intelligenza Artificiale è stata quindi utilizzata come strumento di supporto e consultazione tecnica, analogamente alla documentazione ufficiale e ad altre risorse didattiche,
* mentre tutte le decisioni progettuali e l’implementazione finale sono state effettuate autonomamente.

### Non sono stati utilizzati:
* Generazione automatica di metodi getter/setter - implementati manualmente
* Copilot per autocompletamento
* Strumenti per la generazione di codice ripetitivo

---

## ⚠️ Nota

Per una descrizione più dettagliata su:
- Architettura e layer separation
- Responsabilità delle classi e design decisions
- Implementazione dei principi SOLID
- Persistenza dati e estendibilità futura

Consultare la [Wiki del Repository] 
