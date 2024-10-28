Feature: Quest Game

  Scenario: A1_scenario

    # GIVEN
    Given a new game starts
      And the Adventure Deck's first 10 cards are "F30,SWORD,BATTLE_AXE,F10,LANCE,LANCE,BATTLE_AXE,SWORD,F30,LANCE"
      And Player 1 has a hand of "F5,F5,F15,F15,DAGGER,SWORD,SWORD,HORSE,HORSE,BATTLE_AXE,BATTLE_AXE,LANCE"
      And Player 2 has a hand of "F5,F5,F15,F15,F40,DAGGER,SWORD,HORSE,HORSE,BATTLE_AXE,BATTLE_AXE,EXCALIBUR"
      And Player 3 has a hand of "F5,F5,F5,F15,DAGGER,SWORD,SWORD,SWORD,HORSE,HORSE,BATTLE_AXE,LANCE"
      And Player 4 has a hand of "F5,F15,F15,F40,DAGGER,DAGGER,SWORD,HORSE,HORSE,BATTLE_AXE,LANCE,EXCALIBUR"

    # WHEN
    When Player 1 draws a quest of 4 stages
      And Player 2 sponsors the quest
      And Player 2 builds the 4 stages using the cards "0,7,q,1,5,q,1,3,5,q,1,3,q"

      # Stage 1
      And the Game displays the eligible participants
      And Players "1,3,4" choose to participate in stage 1
      And Player 1 draws and discards the cards in position "0"
      And Player 3 draws and discards the cards in position "0"
      And Player 4 draws and discards the cards in position "0"
      And Player 1 builds attack for current stage using the cards "4,5"
      And Player 3 builds attack for current stage using the cards "4,3"
      And Player 4 builds attack for current stage using the cards "3,6"
      And the Game resolves the attacks for stage 1
      And all participants discard their cards used for their attack in stage 1
      # Stage 2
      And the Game displays the eligible participants
      And Players "1,3,4" choose to participate in stage 1
      And Player 1 draws and discards the cards in position ""
      And Player 3 draws and discards the cards in position ""
      And Player 4 draws and discards the cards in position ""
      And Player 1 builds attack for current stage using the cards "6,5"
      And Player 3 builds attack for current stage using the cards "8,3"
      And Player 4 builds attack for current stage using the cards "5,6"
      And the Game resolves the attacks for stage 2
      And all participants discard their cards used for their attack in stage 2
      # Stage 3
      And the Game displays the eligible participants
      And Players "3,4" choose to participate in stage 3
      And Player 3 draws and discards the cards in position ""
      And Player 4 draws and discards the cards in position ""
      And Player 3 builds attack for current stage using the cards "8,3,5"
      And Player 4 builds attack for current stage using the cards "6,4,8"
      And the Game resolves the attacks for stage 3
      And all participants discard their cards used for their attack in stage 3
      # Stage 4
      And the Game displays the eligible participants
      And Players "3,4" choose to participate in stage 4
      And Player 3 draws and discards the cards in position ""
      And Player 4 draws and discards the cards in position ""
      And Player 3 builds attack for current stage using the cards "6,5,7"
      And Player 4 builds attack for current stage using the cards "3,4,5,7"
      And the Game resolves the attacks for stage 4
      And all participants discard their cards used for their attack in stage 4

      And the Sponsor discards all cards used in the "Q4" quest and draws and trims cards in position "0,0,0,0,0"

    # THEN
    Then Player 2 should have 12 cards in hand
      And Player 1 should have 0 shields
      And Player 3 should have 0 shields
      And Player 4 should have 4 shields
