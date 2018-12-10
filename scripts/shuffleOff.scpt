tell application "System Events"

    set itunesMenuBar to process "iTunes"'s first menu bar
    set controlsMenu to itunesMenuBar's menu bar item "Controls"'s first menu
    set shuffleMenu to controlsMenu's menu item "Shuffle"'s first menu

    set shuffleOnMenuItem to shuffleMenu's menu item "Off"
    set shuffleSongsMenuItem to shuffleMenu's menu item "Songs"

    tell process "iTunes"
        click shuffleOnMenuItem
        click shuffleSongsMenuItem
    end tell

end tell