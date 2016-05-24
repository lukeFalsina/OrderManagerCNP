Per settare correttamente il database é necessario:
1) Installare il client per postgresql (vedi http://www.postgresql.org/download/)
2) Una volta installato, accedere alla shell con il ruolo "postgres". In Linux: sudo -i -u postgres
3) Creare il database "testdb". Comando: createdb testdb
4) Copiare il contenuto del file "populateCNPTables.sql" in un file con lo stesso nome nella directory corrente
5) Eseguire questo nuovo file copiato. Comando: psql -f populateCNPTables.sql testdb
6) Accedere al database testdb. Comando: psql -d testdb
7) Aggiornare la password per questo db e settarla uguale a "user". Comando: \password
8) Controllare che ora esistono righe nella tabella delle pietanze. Comando: select * from pietanza;

Per eseguire l'applicazione, basta fare doppio click sul file 'OrdersManagerCNP.jar'.
Da notare che é richiesto che la JRE7 sia installata sulla macchina che esegue il programma.
Inoltre per non ricevere un errore quando ci si connette al database, il file 'postgresql-9.4.1208.jre7.jar' deve risiedere nella stessa cartella del file 'OrdersManagerCNP.jar'.

Test (eseguito con i parametri di default sulla macchina che possiede il DB in locale):
- Se l'applicazione crasha subito dopo aver scelto i parametri allora il DB non é stato configurato correttamente (oppue 'postgresql-9.4.1208.jre7.jar' non é nella stessa cartella di 'OrdersManagerCNP.jar').
- Se l'applicazione funziona correttamente fino a quando non si finalizza il primo ordine, allora il DB é stato configurato correttamente, ma non le stampanti.

Le stesse considerazioni valgono per le macchine che si connettono in remoto o al DB, o alle stampanti con l'aggravante che ulteriori problemi potrebbero essere dovuti all'uso di un IP incorretto nei settaggi iniziali.
