$(document).ready(function () {
    fetch("http://localhost:8080/players", {
        method: "GET",
    })
        .then(response => response.json())
        .then(players => {
            if (players && players.length > 0) {
                for (let i = 0; i < players.length; i++) {
                    addPlayer()
                }
                for (let i = 0; i < players.length; i++) {
                    const player = players[i];
                    fillInputs(player.firstName, player.lastName, player.email, i);
                    prepareSelectForPlayer(players, i);

                    console.log($('#start-btn'));

                    $('#add-players-btns')[0].setAttribute('hidden', true);
                    $('#start-btn')[0].removeAttribute("hidden");
                }
            } else {
                addPlayer();
            }
        })
});

function addPlayer() {
    const allPlayersBlocks = $('.player-info');
    const nextInx = allPlayersBlocks ? allPlayersBlocks.length : 0;

    const beforeBlock = nextInx ? $(`#player-info-${nextInx - 1}`) : $('#player-mock');
    beforeBlock.after(this.getPlayerContent(nextInx));
}

function submitPlayers() {
    const playersAmount = $('.player-info').length;
    let players = [];
    for (let i = 0; i < playersAmount; i++) {
        players.push(this.collectPlayer(i));
    }
    fetch("http://localhost:8080/players", {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(players)
    }).then(() => location.reload());
}

function submitEnemies() {
    const playersAmount = $('.player-info').length;
    let playersWithEnemies = [];
    for (let i = 0; i < playersAmount; i++) {
        let enemiesSelect = $(`#select-enemies-${i}`).val();
        let enemies = [];
        enemiesSelect.forEach(e => {
            enemies.push(JSON.parse(e));
        });
        playersWithEnemies.push({player: this.collectPlayer(i), enemies: enemies});
    }
    return fetch("http://localhost:8080/enemies", {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(playersWithEnemies)
    })
}

function collectPlayer(index) {
    const firstName = $(`#first-name-${index}`).val();
    const lastName = $(`#last-name-${index}`).val();
    const email = $(`#email-${index}`).val();

    return new Player(firstName, lastName, email);
}

function getPlayerContent(inx) {
    const playerTemplate = $(`#player-info-template`);
    const playerTemplateHtml = playerTemplate[0].innerHTML;
    return `<div class="player-info" id="player-info-${inx}">` +
        playerTemplateHtml.replaceAll(`-{index}`, `-${inx}`) + "</div>";
}

function fillInputs(firstName, lastName, email, index) {
    setReadonlyValue($(`#first-name-${index}`), firstName);
    setReadonlyValue($(`#last-name-${index}`), lastName);
    setReadonlyValue($(`#email-${index}`), email);
}

function setReadonlyValue(input, value) {
    input.val(value);
    input.prop("readonly", true);
}

function prepareSelectForPlayer(players, index) {
    let playersCopy = [...players];
    playersCopy.splice(index, 1);

    let enemiesBlock = $(`#enemies-${index}`)[0];
    enemiesBlock.removeAttribute("hidden");

    let select = $(`#select-enemies-${index}`)[0];
    playersCopy.forEach(p => {
        let option = document.createElement("option");
        option.text = `${p.firstName} ${p.lastName}`;
        option.value = JSON.stringify(p);
        select.add(option);
    })
}

function start() {
    this.submitEnemies().then((response) => {
        fetch("http://localhost:8080/start", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
        })
    })
}

function Player(firstName, lastName, email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
}