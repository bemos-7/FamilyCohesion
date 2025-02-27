package com.bemos.familyohesion.presentation.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.R
import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.presentation.features.profile.utils.ui.FamilyMemberRatingUi
import com.bemos.familyohesion.presentation.features.profile.utils.ui.FamilyMemberUi
import com.bemos.familyohesion.shared.utils.ui.button.CustomButton
import com.bemos.familyohesion.ui.theme.Red
import com.bemos.familyohesion.ui.theme.White

@Composable
fun ProfileContent(
    user: User,
    familyMembers: List<FamilyMember>,
    onFamilyMemberAdd: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Card(
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 19.dp)
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = user.name,
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Column {
                        Text(
                            text = "Почта:"
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Пароль:"
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "${user.email}"
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "***************"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Column {
                    Row {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.edit
                            ),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Изменить",
                            color = Red,
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        Card(
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 19.dp)
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Рейтинг",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Spacer(modifier = Modifier.height(18.dp))
            LazyColumn {
                itemsIndexed(items = familyMembers) { index, familyMember ->
                    FamilyMemberRatingUi(
                        familyMember = familyMember,
                        currentUserName = user.name,
                        memberNumber = index + 1
                    )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
        }
        Spacer(modifier = Modifier.height(7.dp))
        Card(
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 19.dp)
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Семья",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(7.dp))
            }
            LazyColumn {
                items(
                    items = familyMembers
                ) {
                    FamilyMemberUi(
                        familyMember = it,
                        currentUserName = user.name
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                onFamilyMemberAdd(user.familyId)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Red
                            )
                        ) {
                            Text(
                                text = "Добавить"
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Preview
@Composable
fun ProfileContentPreview(modifier: Modifier = Modifier) {
    ProfileContent(
        User(
            email = "something@mail.ru",
            familyId = "",
            name = "Борис",
            role = "Отец",
            userId = ""
        ),
        listOf(
            FamilyMember(
                "Борис",
                "",
                12.1
            ),
            FamilyMember(
                "Антон",
                "",
                12.23
            ),
            FamilyMember(
                "Маша",
                "",
                12.232
            ),
            FamilyMember(
                "Андрес",
                "",
                12.222
            ),
        ),
        onFamilyMemberAdd = {}
    )
}