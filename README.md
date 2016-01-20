Destructo-Block

To navigate to code, follow this path: app/src/main/java/itp341/luu/jonathan/finalprojectluujonathan/

Application Definition Statement

‘Destructo-Block’ is an Android app that is based off the existing game ‘Destructo-Match’. The player 
starts off with a set number of blocks with the goal being to destroy as many blocks as possible. 

Application Rules
- The game starts off with a set number of blocks, with each block being randomly assigned a 
color.
- To destroy blocks, the player must tap on a block that is adjacent to similarly colored blocks. 
- For the example above, if the player taps on any of the blue blocks, all the blue blocks would be 
destroyed. However, if the player attempts to tap on one of the grey blocks, nothing would 
happen because they are not adjacent to one another.
- Once a block is destroyed, it disappears and the remaining blocks ‘fall’ and ‘compress’ to fill in 
the empty space. In the picture above, if the blue blocks are destroyed, the top-left grey block 
would fall down and be atop bottom-left grey block. Additionally, the bottom-right grey block 
would slide to the left and would be adjacent to the bottom-left grey block.
- Sliding only happens if an entire column is missing. For example, if we replace the bottom-
center block with a green block and then remove all the blue blocks, the bottom-right block 
would not slide as it is blocked by the green block.
- Breaking blocks generates points for the player. Breaking more than two blocks with a single tap 
creates a multiplier that scales depending on the number of blocks destroyed with that tap. This 
encourages the player to destroy as many blocks as possible with each tap, rather than tapping 
mindlessly.
- The game starts off at an easy difficulty, as there are fewer colors making it easier to destroy 
blocks. As the user progresses and moves on to further levels, more colors are added making it 
harder to destroy blocks.
- Additionally, there is a set number of the blocks the player must destroy to progress to the next 
level. If the player does not reach the set number of blocks, the game is over.
- There will also be ‘special’ blocks that the user can tap. For example, there will be a ‘bomb’ 
block that destroy several blocks around the bomb block. Another example would be a color-
elimination block, which destroys all of that color block in that level. These blocks are placed 
randomly throughout each level and can be tapped without any adjacent blocks touching them.


Application Details/Features
- The user starts out on a menu. There will be four options:
- New Game starts the player on level one. If it is the player’s first time playing, the application 
will prompt the user to enter in a name. It will also ask the user if they wish to see a tutorial. 
This information will be stored in SharedPreferences.
- Load Game starts the player where he left off. If the player manually quits the application by 
pressing the home button or if the player pauses the game and selects “Quit and Save”, the 
application will store the current game state and resume it if the player selects the “Load Game” 
option on this menu.
- Scores will display a menu of top local scores by the user. There will also be an online score 
section. The player may choose to submit his score to the online leaderboard, which will be held 
by a cloud storage service, and will be ranked based on his score relative to other users who 
have submitted their scores.
- Settings will display several things which will be stored in SharedPreferences.
o Name – This is the name the user entered in on his first play-through. The user is 
allowed to change his name whenever by tapping this Editable field.
o There will be a SFX and BGM slider to adjust the volume of the game. SFX adjusts the 
sound effects when the user taps a block, and BGM adjusts the background music of the 
game. There will also be a mute checkbox that makes the app silent if checked.
o There will be a reset button that resets the user’s local scores, but not his online scores. 
If pressed, it will prompt the user with a second box to make sure that the user wishes 
to clear his/her scores.
o There will be a tutorial button. If pressed, it will prompt the user to see if they wish to 
see the tutorial again. If they press yes to this prompt, the tutorial will begin 
immediately.
o There will be an instructions button. It spawns a separate activity that lays out the rules.
- As mentioned above under Settings, there will be sound effects and background music. There 
will also be a tutorial mode that demonstrates how the game works.
- The application will be locked in portrait mode and will not allow screen rotation.
- When the game is started, there will be a pause button in the top right corner. If the user 
presses this button, a pop-up menu will appear that will let the player resume or quit and save.
- When the game has finished, there will a separate activity that will display the user’s final score 
and his rank. There will be two buttons at the bottom: Return to Menu and Submit Score. If the 
user presses Return to Menu, it simply brings them back to the menu. If the user presses Submit 
Score, it will submit it to the cloud service, which then returns the current leaderboard.
Destructo-Block

To navigate to code, follow this path: app/src/main/java/itp341/luu/jonathan/finalprojectluujonathan/

Application Definition Statement

‘Destructo-Block’ is an Android app that is based off the existing game ‘Destructo-Match’. The player 
starts off with a set number of blocks with the goal being to destroy as many blocks as possible. 

Application Rules
- The game starts off with a set number of blocks, with each block being randomly assigned a 
color.
- To destroy blocks, the player must tap on a block that is adjacent to similarly colored blocks. 
- For the example above, if the player taps on any of the blue blocks, all the blue blocks would be 
destroyed. However, if the player attempts to tap on one of the grey blocks, nothing would 
happen because they are not adjacent to one another.
- Once a block is destroyed, it disappears and the remaining blocks ‘fall’ and ‘compress’ to fill in 
the empty space. In the picture above, if the blue blocks are destroyed, the top-left grey block 
would fall down and be atop bottom-left grey block. Additionally, the bottom-right grey block 
would slide to the left and would be adjacent to the bottom-left grey block.
- Sliding only happens if an entire column is missing. For example, if we replace the bottom-
center block with a green block and then remove all the blue blocks, the bottom-right block 
would not slide as it is blocked by the green block.
- Breaking blocks generates points for the player. Breaking more than two blocks with a single tap 
creates a multiplier that scales depending on the number of blocks destroyed with that tap. This 
encourages the player to destroy as many blocks as possible with each tap, rather than tapping 
mindlessly.
- The game starts off at an easy difficulty, as there are fewer colors making it easier to destroy 
blocks. As the user progresses and moves on to further levels, more colors are added making it 
harder to destroy blocks.
- Additionally, there is a set number of the blocks the player must destroy to progress to the next 
level. If the player does not reach the set number of blocks, the game is over.
- There will also be ‘special’ blocks that the user can tap. For example, there will be a ‘bomb’ 
block that destroy several blocks around the bomb block. Another example would be a color-
elimination block, which destroys all of that color block in that level. These blocks are placed 
randomly throughout each level and can be tapped without any adjacent blocks touching them.


Application Details/Features
- The user starts out on a menu. There will be four options:
- New Game starts the player on level one. If it is the player’s first time playing, the application 
will prompt the user to enter in a name. It will also ask the user if they wish to see a tutorial. 
This information will be stored in SharedPreferences.
- Load Game starts the player where he left off. If the player manually quits the application by 
pressing the home button or if the player pauses the game and selects “Quit and Save”, the 
application will store the current game state and resume it if the player selects the “Load Game” 
option on this menu.
- Scores will display a menu of top local scores by the user. There will also be an online score 
section. The player may choose to submit his score to the online leaderboard, which will be held 
by a cloud storage service, and will be ranked based on his score relative to other users who 
have submitted their scores.
- Settings will display several things which will be stored in SharedPreferences.
o Name – This is the name the user entered in on his first play-through. The user is 
allowed to change his name whenever by tapping this Editable field.
o There will be a SFX and BGM slider to adjust the volume of the game. SFX adjusts the 
sound effects when the user taps a block, and BGM adjusts the background music of the 
game. There will also be a mute checkbox that makes the app silent if checked.
o There will be a reset button that resets the user’s local scores, but not his online scores. 
If pressed, it will prompt the user with a second box to make sure that the user wishes 
to clear his/her scores.
o There will be a tutorial button. If pressed, it will prompt the user to see if they wish to 
see the tutorial again. If they press yes to this prompt, the tutorial will begin 
immediately.
o There will be an instructions button. It spawns a separate activity that lays out the rules.
- As mentioned above under Settings, there will be sound effects and background music. There 
will also be a tutorial mode that demonstrates how the game works.
- The application will be locked in portrait mode and will not allow screen rotation.
- When the game is started, there will be a pause button in the top right corner. If the user 
presses this button, a pop-up menu will appear that will let the player resume or quit and save.
- When the game has finished, there will a separate activity that will display the user’s final score 
and his rank. There will be two buttons at the bottom: Return to Menu and Submit Score. If the 
user presses Return to Menu, it simply brings them back to the menu. If the user presses Submit 
Score, it will submit it to the cloud service, which then returns the current leaderboard.
