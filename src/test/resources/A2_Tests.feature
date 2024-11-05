Feature: Quest Game

  Scenario: A1_scenario

    # GIVEN
    Given a new game starts
      And the Adventure Deck's first 10 cards are "F30,SWORD,BATTLE_AXE,F10,LANCE,LANCE,BATTLE_AXE,SWORD,F30,LANCE"
      And Player 1 has a hand of "F5,F5,F15,F15,DAGGER,SWORD,SWORD,HORSE,HORSE,BATTLE_AXE,BATTLE_AXE,LANCE"
      And Player 2 has a hand of "F5,F5,F15,F15,F40,DAGGER,SWORD,HORSE,HORSE,BATTLE_AXE,BATTLE_AXE,EXCALIBUR"
      And Player 3 has a hand of "F5,F5,F5,F15,DAGGER,SWORD,SWORD,SWORD,HORSE,HORSE,BATTLE_AXE,LANCE"
      And Player 4 has a hand of "F5,F15,F15,F40,DAGGER,DAGGER,SWORD,HORSE,HORSE,BATTLE_AXE,LANCE,EXCALIBUR"

    # Start of Player 1's turn -> Draws Q4 card
    When Player 1 draws a quest of 4 stages

    #THEN
    Then Player 2 sponsors the quest
      And Sponsor builds stage 1 using the cards at position "0,7"
      And Sponsor builds stage 2 using the cards at position "1,5"
      And Sponsor builds stage 3 using the cards at position "1,3,5"
      And Sponsor builds stage 4 using the cards at position "1,3"

      # Stage 1
      And the Game begins the next stage of the quest
      And Players "1,3,4" choose to participate in stage 1 while Players "" decline
      And Player 1 draws and discards the cards at position "0"
      And Player 3 draws and discards the cards at position "0"
      And Player 4 draws and discards the cards at position "0"
      And Player 1 builds attack for current stage using the cards at position "4,5"
      And Player 3 builds attack for current stage using the cards at position "4,3"
      And Player 4 builds attack for current stage using the cards at position "3,6"
      And the Game resolves the attacks for stage 1
      And all participants discard their cards used for their attack in stage 1

      # Stage 2
      And the Game begins the next stage of the quest
      And Players "1,3,4" choose to participate in stage 1 while Players "" decline
      And Player 1 draws and discards the cards at position ""
      And Player 3 draws and discards the cards at position ""
      And Player 4 draws and discards the cards at position ""
      And Player 1 builds attack for current stage using the cards at position "6,5"
      And Player 3 builds attack for current stage using the cards at position "8,3"
      And Player 4 builds attack for current stage using the cards at position "5,6"
      And the Game resolves the attacks for stage 2
      And all participants discard their cards used for their attack in stage 2

      # Stage 3
      And the Game begins the next stage of the quest
      And Players "3,4" choose to participate in stage 3 while Players "" decline
      And Player 3 draws and discards the cards at position ""
      And Player 4 draws and discards the cards at position ""
      And Player 3 builds attack for current stage using the cards at position "8,3,5"
      And Player 4 builds attack for current stage using the cards at position "6,4,8"
      And the Game resolves the attacks for stage 3
      And all participants discard their cards used for their attack in stage 3

      # Stage 4
      And the Game begins the next stage of the quest
      And Players "3,4" choose to participate in stage 4 while Players "" decline
      And Player 3 draws and discards the cards at position ""
      And Player 4 draws and discards the cards at position ""
      And Player 3 builds attack for current stage using the cards at position "6,5,7"
      And Player 4 builds attack for current stage using the cards at position "3,4,5,7"
      And the Game resolves the attacks for stage 4
      And all participants discard their cards used for their attack in stage 4

      # End of Q4
      And the Sponsor discards all cards used in the "Q4" quest and draws and trims cards in position "0,0,0,0,0"

      And Player 3 should have 0 shields
      And Player 4 should have 4 shields
      And Player 1 should have a hand of size 9
      And Player 1 should have a hand containing the cards "F5,F10,F15,F15,F30,HORSE,BATTLE_AXE,BATTLE_AXE,LANCE"
      And Player 3 should have a hand of size 5
      And Player 3 should have a hand containing the cards "F5,F5,F15,F30,SWORD"
      And Player 4 should have a hand of size 4
      And Player 4 should have a hand containing the cards "F15,F15,F40,LANCE"
      And Player 2 should have a hand of size 12

  Scenario: 2winner_game_2winner_quest

    # GIVEN
    Given a new game starts
      And the Adventure Deck's first 27 cards are "DAGGER,F5,DAGGER,HORSE,SWORD,SWORD,HORSE,F10,F30,DAGGER,F5,DAGGER,HORSE,SWORD,SWORD,HORSE,F10,F30,HORSE,F10,F30,F5,F10,F35,F40,F50,F70"
      And Player 1 has a hand of "F5,F5,F15,F15,DAGGER,SWORD,HORSE,HORSE,HORSE,BATTLE_AXE,LANCE,EXCALIBUR"
      And Player 2 has a hand of "F5,F5,F15,F15,SWORD,SWORD,HORSE,HORSE,BATTLE_AXE,LANCE,EXCALIBUR,EXCALIBUR"
      And Player 3 has a hand of "F5,F5,F15,F15,DAGGER,SWORD,HORSE,HORSE,HORSE,BATTLE_AXE,LANCE,EXCALIBUR"
      And Player 4 has a hand of "F5,F5,F15,F15,SWORD,HORSE,HORSE,BATTLE_AXE,BATTLE_AXE,LANCE,EXCALIBUR,EXCALIBUR"

    # Start of Player 1's turn -> Draws Q4 card
    When Player 1 draws a quest of 4 stages

    Then Player 1 sponsors the quest
      And Sponsor builds stage 1 using the cards at position "0,5"
      And Sponsor builds stage 2 using the cards at position "0,7"
      And Sponsor builds stage 3 using the cards at position "0,5"
      And Sponsor builds stage 4 using the cards at position "0,4"

      # Stage 1
      And the Game begins the next stage of the quest
      And Players "2,3,4" choose to participate in stage 1 while Players "" decline
      And Player 2 draws and discards the cards at position "0"
      And Player 3 draws and discards the cards at position "0"
      And Player 4 draws and discards the cards at position "0"
      And Player 2 builds attack for current stage using the cards at position "3,4"
      And Player 3 builds attack for current stage using the cards at position "4"
      And Player 4 builds attack for current stage using the cards at position "3,4"
      And the Game resolves the attacks for stage 1
      And all participants discard their cards used for their attack in stage 1

      # Stage 2
      And the Game begins the next stage of the quest
      And Players "2,4" choose to participate in stage 2 while Players "" decline
      And Player 2 draws and discards the cards at position ""
      And Player 4 draws and discards the cards at position ""
      And Player 2 builds attack for current stage using the cards at position "3,4"
      And Player 4 builds attack for current stage using the cards at position "3,4"
      And the Game resolves the attacks for stage 2
      And all participants discard their cards used for their attack in stage 2

      # Stage 3
      And the Game begins the next stage of the quest
      And Players "2,4" choose to participate in stage 3 while Players "" decline
      And Player 2 draws and discards the cards at position ""
      And Player 4 draws and discards the cards at position ""
      And Player 2 builds attack for current stage using the cards at position "5,6"
      And Player 4 builds attack for current stage using the cards at position "5,4"
      And the Game resolves the attacks for stage 3
      And all participants discard their cards used for their attack in stage 3

      # Stage 4
      And the Game begins the next stage of the quest
      And Players "2,4" choose to participate in stage 4 while Players "" decline
      And Player 2 draws and discards the cards at position ""
      And Player 4 draws and discards the cards at position ""
      And Player 2 builds attack for current stage using the cards at position "7,4"
      And Player 4 builds attack for current stage using the cards at position "7,4"
      And the Game resolves the attacks for stage 4
      And all participants discard their cards used for their attack in stage 4

      # End of Q3
      And the Sponsor discards all cards used in the "Q4" quest and draws and trims cards in position "0,0,0,0"

      And Player 2 should have 4 shields
      And Player 4 should have 4 shields

      # Start of Player 2's turn -> Draws Q3 card
      And the turn of Player 2 has begun
      And Player 2 draws a quest of 3 stages
      And Player 3 sponsors the quest
      And Sponsor builds stage 1 using the cards at position "0"
      And Sponsor builds stage 2 using the cards at position "1"
      And Sponsor builds stage 3 using the cards at position "1,2"

      # Stage 1
      And the Game begins the next stage of the quest
      And Players "2,4" choose to participate in stage 1 while Players "1" decline
      And Player 2 draws and discards the cards at position "0"
      And Player 4 draws and discards the cards at position "0"
      And Player 2 builds attack for current stage using the cards at position "5"
      And Player 4 builds attack for current stage using the cards at position "5"
      And the Game resolves the attacks for stage 1
      And all participants discard their cards used for their attack in stage 1

      # Stage 2
      And the Game begins the next stage of the quest
      And Players "2,4" choose to participate in stage 2 while Players "" decline
      And Player 2 draws and discards the cards at position "0"
      And Player 4 draws and discards the cards at position "0"
      And Player 2 builds attack for current stage using the cards at position "6"
      And Player 4 builds attack for current stage using the cards at position "6"
      And the Game resolves the attacks for stage 2
      And all participants discard their cards used for their attack in stage 2

      # Stage 3
      And the Game begins the next stage of the quest
      And Players "2,4" choose to participate in stage 3 while Players "" decline
      And Player 2 draws and discards the cards at position "0"
      And Player 4 draws and discards the cards at position "0"
      And Player 2 builds attack for current stage using the cards at position "7"
      And Player 4 builds attack for current stage using the cards at position "7"
      And the Game resolves the attacks for stage 3
      And all participants discard their cards used for their attack in stage 3

      # End of Q3
      And the Sponsor discards all cards used in the "Q3" quest and draws and trims cards in position "0,0"

      # A winner is found and they are displayed
      And The winners of the game are displayed

      And Player 2 should have 7 shields
      And Player 4 should have 7 shields
      And Player 2 should be a winner
      And Player 4 should be a winner

  Scenario: 0_winner_quest

    # GIVEN
    Given a new game starts
      And Player 1 has a hand of "F5,F10,F15,F15,DAGGER,SWORD,HORSE,HORSE,HORSE,BATTLE_AXE,LANCE,EXCALIBUR"

    # Start of Player 1's turn -> Draws Q2 card
    When Player 1 draws a quest of 2 stages

    Then Player 1 sponsors the quest
      And Sponsor builds stage 1 using the cards at position "0"
      And Sponsor builds stage 2 using the cards at position "0"

      And Player 1 should have a hand of size 10

      # Stage 1
      And the Game begins the next stage of the quest
      And Players "2,3,4" choose to participate in stage 1 while Players "" decline
      And Player 2 draws and discards the cards at position "0"
      And Player 3 draws and discards the cards at position "0"
      And Player 4 draws and discards the cards at position "0"
      And Player 2 builds attack for current stage using the cards at position ""
      And Player 3 builds attack for current stage using the cards at position ""
      And Player 4 builds attack for current stage using the cards at position ""
      And the Game resolves the attacks for stage 1
      And all participants discard their cards used for their attack in stage 1

      # End of Q2
      And the Sponsor discards all cards used in the "Q2" quest and draws and trims cards in position "0,0"

      And Player 1 should have 0 shields
      And Player 2 should have 0 shields
      And Player 3 should have 0 shields
      And Player 4 should have 0 shields

      And Player 1 should have a hand of size 12
      And Player 2 should have a hand of size 12
      And Player 3 should have a hand of size 12
      And Player 4 should have a hand of size 12

  Scenario: 1winner_game_with_events

    # GIVEN
    Given a new game starts
      And the Adventure Deck's first 39 cards are "DAGGER,BATTLE_AXE,DAGGER,HORSE,SWORD,SWORD,HORSE,F10,F30,DAGGER,LANCE,DAGGER,F10,F20,F40,F5,DAGGER,DAGGER,HORSE,BATTLE_AXE,SWORD,F5,BATTLE_AXE,SWORD,DAGGER,F10,HORSE,F15,LANCE,DAGGER,F40,F25,F10,DAGGER,LANCE,F20,EXCALIBUR,BATTLE_AXE,SWORD"

      And Player 1 has a hand of "F5,F5,F15,F15,DAGGER,DAGGER,DAGGER,SWORD,HORSE,HORSE,HORSE,LANCE"
      And Player 2 has a hand of "F5,F5,F15,F15,SWORD,SWORD,HORSE,HORSE,BATTLE_AXE,LANCE,EXCALIBUR,EXCALIBUR"
      And Player 3 has a hand of "F5,F5,F15,F15,DAGGER,SWORD,HORSE,HORSE,HORSE,BATTLE_AXE,LANCE,EXCALIBUR"
      And Player 4 has a hand of "F5,F5,F15,F15,SWORD,HORSE,HORSE,BATTLE_AXE,BATTLE_AXE,LANCE,EXCALIBUR,EXCALIBUR"

    # Start of Player 1's turn -> Draws Q4 card
    When Player 1 draws a quest of 4 stages

    Then Player 1 sponsors the quest
      And Sponsor builds stage 1 using the cards at position "0"
      And Sponsor builds stage 2 using the cards at position "0,4"
      And Sponsor builds stage 3 using the cards at position "0"
      And Sponsor builds stage 4 using the cards at position "0,1"

      # Stage 1
      And the Game begins the next stage of the quest
      And Players "2,3,4" choose to participate in stage 1 while Players "" decline
      And Player 2 draws and discards the cards at position "0"
      And Player 3 draws and discards the cards at position "0"
      And Player 4 draws and discards the cards at position "0"
      And Player 2 builds attack for current stage using the cards at position "3"
      And Player 3 builds attack for current stage using the cards at position "3"
      And Player 4 builds attack for current stage using the cards at position "3"
      And the Game resolves the attacks for stage 1
      And all participants discard their cards used for their attack in stage 1

      # Stage 2
      And the Game begins the next stage of the quest
      And Players "2,3,4" choose to participate in stage 2 while Players "" decline
      And Player 2 draws and discards the cards at position ""
      And Player 3 draws and discards the cards at position ""
      And Player 4 draws and discards the cards at position ""
      And Player 2 builds attack for current stage using the cards at position "3"
      And Player 3 builds attack for current stage using the cards at position "5"
      And Player 4 builds attack for current stage using the cards at position "4"
      And the Game resolves the attacks for stage 2
      And all participants discard their cards used for their attack in stage 2

      # Stage 3
      And the Game begins the next stage of the quest
      And Players "2,3,4" choose to participate in stage 3 while Players "" decline
      And Player 2 draws and discards the cards at position ""
      And Player 3 draws and discards the cards at position ""
      And Player 4 draws and discards the cards at position ""
      And Player 2 builds attack for current stage using the cards at position "8"
      And Player 3 builds attack for current stage using the cards at position "8"
      And Player 4 builds attack for current stage using the cards at position "9"
      And the Game resolves the attacks for stage 3
      And all participants discard their cards used for their attack in stage 3

      # Stage 4
      And the Game begins the next stage of the quest
      And Players "2,3,4" choose to participate in stage 4 while Players "" decline
      And Player 2 draws and discards the cards at position ""
      And Player 3 draws and discards the cards at position ""
      And Player 4 draws and discards the cards at position ""
      And Player 2 builds attack for current stage using the cards at position "9"
      And Player 3 builds attack for current stage using the cards at position "5,6"
      And Player 4 builds attack for current stage using the cards at position "4,8"
      And the Game resolves the attacks for stage 4
      And all participants discard their cards used for their attack in stage 4

      # End of Q4
      And the Sponsor discards all cards used in the "Q4" quest and draws and trims cards in position "11,11,11,11"

      And Player 1 should have 0 shields
      And Player 2 should have 4 shields
      And Player 3 should have 4 shields
      And Player 4 should have 4 shields

      # Start of Player 2's turn -> Draws Plague card
      And the turn of Player 2 has begun
      And Player 2 draws a Plague card
      And Player 2 should have 2 shields

      # Start of Player 3's turn -> Draws Prosperity card
      And the turn of Player 3 has begun
      And Player 3 draws a Prosperity card
      And Player 3 immediately draws 2 Adventure cards and discards the cards at position ""
      And Player 4 immediately draws 2 Adventure cards and discards the cards at position ""
      And Player 1 immediately draws 2 Adventure cards and discards the cards at position "11,11"
      And Player 2 immediately draws 2 Adventure cards and discards the cards at position "0"
      And Player 3 should have a hand of size 12
      And Player 4 should have a hand of size 12
      And Player 1 should have a hand of size 12
      And Player 2 should have a hand of size 12

      # Start of Player 4's turn -> Draws Queen's favor card
      And the turn of Player 4 has begun
      And Player 4 draws a Queen's favor card
      And Player 4 immediately draws 2 Adventure cards and discards the cards at position "11,11"
      And Player 4 should have a hand of size 12

      # Start of Player 1's turn -> Draws Q3 card
      And the turn of Player 1 has begun
      And Player 1 draws a quest of 3 stages
      And Player 1 sponsors the quest

      # The Sponsor builds the quest
      And Sponsor builds stage 1 using the cards at position "2"
      And Sponsor builds stage 2 using the cards at position "2"
      And Sponsor builds stage 3 using the cards at position "2"

      # Stage 1
      And the Game begins the next stage of the quest
      And Players "2,3,4" choose to participate in stage 1 while Players "" decline
      And Player 2 draws and discards the cards at position "0"
      And Player 3 draws and discards the cards at position "0"
      And Player 4 draws and discards the cards at position "1"
      And Player 2 builds attack for current stage using the cards at position "4"
      And Player 3 builds attack for current stage using the cards at position "5"
      And Player 4 builds attack for current stage using the cards at position "6"
      And the Game resolves the attacks for stage 1
      And all participants discard their cards used for their attack in stage 1

      # Stage 2
      And the Game begins the next stage of the quest
      And Players "2,3" choose to participate in stage 2 while Players "" decline
      And Player 2 draws and discards the cards at position ""
      And Player 3 draws and discards the cards at position ""
      And Player 2 builds attack for current stage using the cards at position "9"
      And Player 3 builds attack for current stage using the cards at position "6"
      And the Game resolves the attacks for stage 2
      And all participants discard their cards used for their attack in stage 2

      # Stage 3
      And the Game begins the next stage of the quest
      And Players "2,3" choose to participate in stage 3 while Players "" decline
      And Player 2 draws and discards the cards at position ""
      And Player 3 draws and discards the cards at position ""
      And Player 2 builds attack for current stage using the cards at position "11"
      And Player 3 builds attack for current stage using the cards at position "9"
      And the Game resolves the attacks for stage 3
      And all participants discard their cards used for their attack in stage 3

      # End of Q3
      And the Sponsor discards all cards used in the "Q4" quest and draws and trims cards in position "0,0,0"

      # A winner is found and they are displayed
      And The winners of the game are displayed

      And Player 2 should have 5 shields
      And Player 3 should have 7 shields
      And Player 3 should be a winner











































