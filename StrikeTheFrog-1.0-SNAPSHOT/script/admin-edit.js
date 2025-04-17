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