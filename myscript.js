function click(e) {

    chrome.tabs.executeScript(null, {
        file: "nija.js"
    });

    chrome.tabs.executeScript(null, {
        file: "Item.js"
    });
    chrome.tabs.executeScript(null, {
        file: "ExplicitModjson.js"
    });
    chrome.tabs.executeScript(null, {
        file: "Gem.js"
    });
    chrome.tabs.executeScript(null, {
        file: "Unique.js"
    });
    chrome.tabs.executeScript(null, {
        file: "mods.js"
    });
    chrome.tabs.executeScript(null, {
        file: "try2cn.js"
    });

    window.close();
}

document.addEventListener('DOMContentLoaded',
function() {
    var divs = document.querySelectorAll('div');
    for (var i = 0; i < divs.length; i++) {
        divs[i].addEventListener('click', click);
    }

});