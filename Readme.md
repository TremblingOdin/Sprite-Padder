#ImagePad v0.1#

The intent of this application is to quickly add padding to a sprite sheet. I decided to make it instead of manually adding padding to 3 different sprite sheets while developing a game.

In the GUI the original image will be displayed in the *top left*. The altered image with padding will be displayed in the *top right*. You can zoom into each with a left click, and zoom out with a right click.

The text in the bottom left will display any errors, user caused or programming errors.

The program expects the sprite sheet to be in uniform grids, it will take the RGB value of the pixels on the borders of the grid. It will then use those values to add more pixels. It can take a negative value, but at the moment it only removes that many layers of pixels from the grid.
