package com.devid_academy.common.home

import com.devid_academy.common.R

data class HomeUiState (
    val gameInfoList : List<GameInfo> = listOf(
        GameInfo(
            id = "pendu"
            ,                imageRes = R.drawable.game_one_logo,
            description = "Testez votre vocabulaire ! Trouvez le mot caché avant que le pendu ne tombe ...",
            name = "LE JEU DU PENDU"
        ),
        GameInfo(
            id = "motus",
            imageRes = R.drawable.game_two_logo,
            description = "Testez votre esprit ! Trouvez le mot caché en un minimum de tentatives. ",
            name = "MOTUS-MOTUS"
        ),
        GameInfo(
            id = "synonymes",
            imageRes = R.drawable.game_three_logo,
            description = "Amusez-vous à jongler avec les mots ! Trouvez les synonymes et brillez en vocabulaire.",
            name = "LE JEU DES SYNONYMES "
        ),
        GameInfo(
            id = "bac",
            imageRes = R.drawable.game_four_logo,
            description = "Mettez vos neurones à l'épreuve ! Remplissez chaque catégorie avec des mots uniques",
            name = "LE PETIT BAC"
        )
    ),
    val selectedGameId: String = "",
    val isDisplayingQuitApp: Boolean = false
)