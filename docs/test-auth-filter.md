| Scénario | URL | Session attendue | Résultat attendu |
|---|---|---|---|
| Accès infirmier sans session | `/infirmier/patients` | Aucune session | Redirection vers `/login` |
| Accès infirmier avec rôle généraliste | `/infirmier/patients` | Session active avec `utilisateur` défini et `role=GENERALISTE` | HTTP 403 |
| Accès infirmier avec rôle infirmier | `/infirmier/patients` | Session active avec `utilisateur` défini et `role=INFIRMIER` | HTTP 200 |
| Accès au chemin public de connexion | `/login` | Aucune session | HTTP 200 |