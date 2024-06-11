package com.devid_academy.hangman

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.ui.composables.KeyboardUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class HangmanViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(HangmanUiState())
    fun observeHangmanUiState(): StateFlow<HangmanUiState> = _uiState

    private val _keyboardUiState = MutableStateFlow(KeyboardUiState())
    fun observeKeyboardUiState(): StateFlow<KeyboardUiState> = _keyboardUiState


    val wordList = listOf(

        "chapeau", "ordinateur", "bibliotheque", "camion", "pomme", "soleil", "maison", "jardin", "papillon", "montagne",
        "livre", "ciel", "eau", "chat", "chien", "voiture", "fleur", "arbre", "lumiere", "nuit",
        "jour", "heure", "minute", "seconde", "montre", "temps", "annee", "mois", "semaine", "jour",
        "soleil", "lune", "etoile", "ciel", "nuage", "pluie", "neige", "vent", "orage",
        "ville", "village", "champ", "foret", "riviere", "mer", "ocean", "plage", "sable", "rocher",
        "colline", "vallee", "prairie", "desert", "terre", "monde", "pays", "continent", "ile", "lac",
        "pont", "route", "chemin", "sentier", "autoroute", "tunnel", "rail", "train", "avion", "bateau",
        "port", "aeroport", "station", "velo", "moto", "bus", "tramway", "metro", "taxi", "voiture",
        "feu", "glace", "air", "metal", "bois", "pierre", "verre", "plastique", "papier",
        "stylo", "crayon", "gomme", "regle", "ciseaux", "pinceau", "peinture", "dessin", "couleur", "forme",
        "rond", "carre", "triangle", "rectangle", "cercle", "ovale", "ligne", "point", "surface", "volume",
        "espace", "dimension", "taille", "longueur", "largeur", "hauteur", "profondeur", "distance", "mesure",
        "poids", "masse", "volume", "densite", "quantite", "nombre", "total", "somme", "difference", "produit",
        "quotient", "fraction", "pourcentage", "ratio", "proportion", "variable", "equation", "fonction", "graphique",
        "analyse", "statistique", "probabilite", "logique", "raisonnement", "theorie", "principe", "loi", "regle",
        "norme", "standard", "qualite", "performance", "efficacite", "productivite", "innovation", "technologie",
        "science", "recherche", "decouverte", "invention", "creation", "imagination", "inspiration", "idee", "concept",
        "projet", "plan", "strategie", "objectif", "but", "mission", "vision", "ambition", "defi", "succes",
        "echec", "risque", "opportunite", "possibilite", "potentiel", "talent", "competence", "habilete", "capacite",
        "experience", "connaissance", "sagesse", "intelligence", "reflexion", "meditation", "pensee", "idee", "opinion",
        "point de vue", "perspective", "approche", "methode", "technique", "procede", "processus", "systeme", "structure",
        "organisation", "entreprise", "societe", "institution", "gouvernement", "etat", "nation", "peuple", "communaute",
        "societe", "culture", "tradition", "coutume", "religion", "croyance", "foi", "spiritualite", "ame", "esprit",
        "corps", "sante", "nutrition", "alimentation", "nourriture", "boisson", "eau", "lait", "jus",
        "dejeuner", "diner", "snack", "dessert", "fruit", "legume", "viande", "poisson", "oeuf", "pain",
        "pate", "riz", "haricot", "lentille", "noix", "graine", "huile", "beurre", "fromage", "yaourt",
        "glace", "gateau", "bonbon", "chocolat", "sucre", "sel", "poivre", "epice", "herbe", "arome",
        "gout", "saveur", "parfum", "odeur", "son", "bruit", "silence", "musique", "chanson", "melodie",
        "rythme", "instrument", "voix", "parole", "langue", "mot", "phrase", "texte", "histoire", "recit",
        "poeme", "roman", "livre", "journal", "article", "lettre", "message", "communication", "information",
        "nouvelle", "actualite", "evenement", "fait", "donnee", "preuve", "exemple", "cas", "situation",
        "contexte", "circonstance", "probleme", "question", "enquete", "analyse", "etude", "recherche",
        "experience", "experimentation", "observation", "mesure", "test", "essai", "evaluation", "appreciation",
        "jugement", "critique", "commentaire", "discussion", "debat", "dialogue", "conversation", "echange",
        "interaction", "relation", "lien", "connexion", "reseau", "internet", "web", "site", "page", "blog",
        "forum", "chat", "email", "telephone", "mobile", "smartphone", "application", "logiciel", "programme",
        "systeme", "base de donnees", "serveur", "ordinateur", "tablet", "ecran", "clavier", "souris",
        "imprimante", "scanner", "appareil photo", "camera", "video", "film", "serie", "emission", "programme",
        "chaine", "canal", "radio", "media", "publicite", "marketing", "vente", "achat", "produit", "service",
        "client", "consommateur", "marche", "commerce", "economie", "finance", "argent", "monnaie", "banque",
        "credit", "dette", "investissement", "bourse", "action", "obligation", "fond", "capital", "richesse",
        "pauvrete", "inegalite", "emploi", "travail", "profession", "carriere", "entreprise", "industrie",
        "production", "fabrication", "construction", "batiment", "architecture", "design", "art", "peinture",
        "sculpture", "photographie", "cinema", "theatre", "danse", "musique", "litterature", "poesie",
        "philosophie", "histoire", "geographie", "politique", "droit", "justice", "liberte", "egalite",
        "fraternite", "paix", "guerre", "conflit", "violence", "securite", "protection", "defense", "armee",
        "police", "loi", "reglement", "norme", "standard", "qualite", "securite", "environnement", "nature",
        "ecologie", "climat", "meteo", "planete", "univers", "espace", "galaxie", "etoile",
        "planete", "lune", "satellite", "asteroide", "comete", "trou noir", "quasar", "nebuleuse", "cosmos",
        "astronomie", "astrophysique", "physique", "chimie", "biologie", "genetique", "medecine", "sante",
        "psychologie", "sociologie", "anthropologie", "archeologie", "paleontologie", "zoologie", "botanique",
        "ecologie", "geologie", "mineralogie", "oceanographie", "meteorologie", "climatologie", "geographie",
        "cartographie", "topographie", "urbanisme", "amenagement", "paysage", "jardin", "parc", "foret",
        "champ", "ferme", "agriculture", "elevage", "peche", "chasse", "cueillette", "horticulture",
        "floriculture", "arboriculture", "viticulture", "oleiculture", "apiculture", "sylviculture",
        "aquaculture", "pisciculture", "halieutique", "faune", "flore", "animal", "plante", "insecte",
        "oiseau", "poisson", "mammifere", "reptile", "amphibien", "mollusque", "crustace", "arachnide",
        "algue", "champignon", "bacterie", "virus", "cellule", "gene", "adn", "arn", "proteine", "enzyme",
        "hormone", "vitamine", "mineral", "nutriment", "aliment", "boisson", "eau", "air", "feu",
        "energie", "force", "mouvement", "vitesse", "acceleration", "inertie", "masse", "poids", "gravite",
        "pression", "temperature", "chaleur", "lumiere", "couleur", "son", "musique", "langage", "parole",
        "ecriture", "lecture", "apprentissage", "enseignement", "education", "formation", "culture", "art",
        "science", "technologie", "innovation", "recherche", "developpement", "sante", "medecine", "hygiene",
        "nutrition", "alimentation", "sport", "loisir", "divertissement", "jeu", "jouet", "hobby", "passion",
        "amour", "amitie", "famille", "relation", "communication", "expression", "emotion", "sentiment",
        "pensee", "idee", "creativite", "imagination", "inspiration", "motivation", "volonte", "determination",
        "perseverance", "courage", "bravoure", "force", "puissance", "energie", "vitalite", "sante",
        "bonheur", "joie", "plaisir", "satisfaction", "reussite", "succes", "victoire", "triomphe", "gloire",
        "honneur", "respect", "dignite", "valeur", "principe", "ethique", "morale", "vertu", "justice",
        "droit", "liberte", "egalite", "fraternite", "solidarite", "entraide", "cooperation", "partage",
        "generosite", "altruisme", "bienveillance", "compassion", "empathie", "sympathie", "affection",
        "tendresse", "amour", "passion", "desir", "attraction", "seduction", "charme", "beaute", "elegance",
        "grace", "style", "mode", "tendance", "nouveaute", "originalite", "unicite", "exclusivite", "rarete",
        "preciosite", "luxe", "confort", "aisance", "richesse", "abondance", "prosperite", "croissance",
        "developpement", "expansion", "evolution", "progres", "amelioration", "optimisation", "efficacite",
        "performance", "qualite", "excellence", "perfection", "maitrise", "competence", "expertise",
        "professionnalisme", "technique", "methode", "strategie", "tactique", "planification",
        "organisation", "gestion", "administration", "direction", "leadership", "autorite", "commandement",
        "controle", "regulation", "normalisation", "standardisation", "certification", "homologation",
        "accreditation", "validation", "verification", "inspection", "audit", "evaluation", "appreciation",
        "jugement", "critique", "analyse", "synthese", "conclusion", "decision", "choix", "option",
        "alternative", "solution", "remede", "traitement", "cure", "therapie", "rehabilitation", "recuperation",
        "restauration", "renovation", "reparation", "entretien", "maintenance", "conservation", "preservation",
    )


    fun setWord(){

        _uiState.value = HangmanUiState()

        val randomNumber = (Math.random() * 100 % wordList.size).roundToInt() -1

        viewModelScope.launch {
            wordList[randomNumber].forEach { value ->
                delay(100)
                _uiState.value = _uiState.value.copy(
                    wordToDiscover = _uiState.value.wordToDiscover.toMutableList().also {
                        it.add(HangmanLetter(value, false))
                    }
                )
            }
        }

        /*
        _state.value = _state.value.copy(
            wordToDiscover = wordList[randomNumber].map {
                HangmanLetter(it, false)
            }
        )
        */
    }


    fun onLetterClicked(letterClicked: Char) {

        /*
        var hasNoCoveredLetter = false

        _state.value.wordToDiscover.forEach {
            if(!it.isDiscovered)
                hasNoCoveredLetter = true
        }*/

        // TODO : check if word has been found => make states genre : first game, replay, game finished, ...
        // TODO : check if letter has been clicked before ;
        // TODO : pouvoir révéler le mot

        if(_uiState.value.counter > 0 //  && !hasNoCoveredLetter
            ){
            val wordToDiscover = _uiState.value.wordToDiscover.toMutableList()
            var isRightGuess = false

            wordToDiscover.forEachIndexed { index, hangmanLetter ->
                if (hangmanLetter.letter.lowercaseChar() == letterClicked.lowercaseChar()) {
                    isRightGuess = true
                    wordToDiscover[index] = hangmanLetter.copy(isDiscovered = true)
                }
            }

            val keyboardLetterList = _keyboardUiState.value.keyboardLetterList.map { row ->
                row.map { key ->
                    if (key.letter.lowercaseChar() == letterClicked.lowercaseChar()) {
                        key.copy(hasBeenPressed = true, keyColor = if (isRightGuess) Color(
                            0xFF54DB1A
                        ) else Color(0xFFDB1A1A)
                        )
                    } else {
                        key
                    }
                }
            }.toMutableList()

            _keyboardUiState.value = _keyboardUiState.value.copy(keyboardLetterList = keyboardLetterList)

            if(isRightGuess)
                _uiState.value = _uiState.value.copy(wordToDiscover = wordToDiscover)
            else
                _uiState.value = _uiState.value.copy(counter = _uiState.value.counter - 1)
        }
    }

    fun onReplay(){

    }


}

