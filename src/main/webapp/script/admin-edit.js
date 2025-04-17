/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function updatePlayerName(playerID, targetField) {
    console.log('updatePlayerName called with playerID:', playerID, 'targetField:', targetField);
    playerID = playerID.trim();
    if (!playerID || isNaN(playerID)) {
        console.log('Invalid Player ID');
        alert('Please enter a valid numeric Player ID');
        return;
    }
    console.log('Sending fetch request for playerID:', playerID);
    var url = './Dashboard?action=get-player&playerID=' + playerID;
    fetch(url)
            .then(function (response) {
                console.log('Fetch response status:', response.status);
                if (!response.ok) {
                    throw new Error('HTTP error! status: ' + response.status);
                }
                return response.json();
            })
            .then(function (data) {
                console.log('Received data:', data);
                if (data.playerName) {
                    console.log('Updating', targetField, 'with value:', data.playerName);
                    document.getElementById(targetField).value = data.playerName;

                    // Xóa phần cập nhật dropdown winner vì không cần thiết nữa
                } else {
                    console.log('Player not found!');
                    alert('Player not found!');
                }
            })
            .catch(function (error) {
                console.error('Fetch Error:', error);
                alert('Error fetching player data: ' + error.message);
            });
}

// Add this function to update winner based on scores
function updateWinner() {
    console.log('updateWinner called');
    const scoreP1 = parseInt(document.getElementById('scorep1').value) || 0;
    const scoreP2 = parseInt(document.getElementById('scorep2').value) || 0;
    const player1Name = document.getElementById('player1Name').value;
    const player2Name = document.getElementById('player2Name').value;
    const winnerPlayerNameField = document.getElementById('winnerPlayerName');
    
    console.log('Scores:', scoreP1, scoreP2);
    console.log('Players:', player1Name, player2Name);
    
    if (scoreP1 > scoreP2) {
        winnerPlayerNameField.value = player1Name;
    } else if (scoreP2 > scoreP1) {
        winnerPlayerNameField.value = player2Name;
    } else {
        winnerPlayerNameField.value = "Tie or pending result";
    }
}


function validateMatchTime() {
    const form = document.getElementById('game-details-form');
    
    form.addEventListener('submit', function(event) {
        const matchTimeInput = document.getElementById('matchtime');
        const tournamentStartTimeEl = document.getElementById('tournament-start-time');
        const tournamentEndTimeEl = document.getElementById('tournament-end-time');
        
        if (tournamentStartTimeEl && tournamentEndTimeEl && matchTimeInput && matchTimeInput.value) {
            const tournamentStartTime = new Date(tournamentStartTimeEl.value).getTime();
            const tournamentEndTime = new Date(tournamentEndTimeEl.value).getTime();
            const matchTime = new Date(matchTimeInput.value).getTime();
            
            console.log("Start time:", new Date(tournamentStartTime).toLocaleString());
            console.log("End time:", new Date(tournamentEndTime).toLocaleString());
            console.log("Match time:", new Date(matchTime).toLocaleString());
            
            // Check if match time is within tournament time range
            if (matchTime < tournamentStartTime || matchTime > tournamentEndTime) {
                event.preventDefault();
                alert('Match time must be within tournament start and end time.\nValid range: ' + 
                      new Date(tournamentStartTime).toLocaleString() + ' to ' + 
                      new Date(tournamentEndTime).toLocaleString());
                return false;
            }
        }
        
        return true;
    });
}

// Đảm bảo hàm được gọi khi trang tải xong
document.addEventListener('DOMContentLoaded', function() {
    validateMatchTime();
    if (typeof updateWinner === 'function') {
        updateWinner();
    }
});