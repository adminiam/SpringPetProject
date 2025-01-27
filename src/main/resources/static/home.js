async function fetchAndSaveContextId() {
    try {
        const response = await fetch('/getContext', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const userContextId = await response.text();  // Сервер возвращает строку (ID)

        if (userContextId) {
            localStorage.setItem('userContextId', userContextId);
        } else {
            console.warn('ID не найден в объекте UserContext.');
        }
    } catch (error) {
        console.error('Ошибка при получении контекста пользователя:', error);
    }
}

function displayMessages(messages) {
    const messageDisplay = document.getElementById('message-display');
    messageDisplay.innerHTML = '';

    messages.forEach(msg => {
        const messageElement = document.createElement('div');
        messageElement.classList.add('message');
        const sender = msg.senderId || 'Unknown Sender';
        messageElement.innerHTML = `<span class="message-author">${sender}:</span> ${msg.message}`;
        messageDisplay.appendChild(messageElement);
    });

    messageDisplay.scrollTop = messageDisplay.scrollHeight;
}

async function fetchMessages(key) {
    try {
        const response = await fetch(`/getMessageClient?key=${key}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const messages = await response.json();
        const parsedMessages = messages.map(entry => {
            const messageData = Object.values(entry)[0];
            return {
                id: messageData.id,
                message: messageData.message,
                senderId: messageData.senderId,
                receiverId: messageData.receiverId
            };
        });

        displayMessages(parsedMessages);
    } catch (error) {
        console.error('Ошибка при получении сообщений:', error);
    }
}

function startMessagePolling() {
    const userId = localStorage.getItem('userContextId');

    if (!userId) {
        console.error('User ID не найден. Убедитесь, что ID сохранен в localStorage.');
        return;
    }

    setInterval(() => {
        fetchMessages(userId);
    }, 1000);
}

async function initializeApp() {
    await fetchAndSaveContextId();
    startMessagePolling();
}

window.onload = initializeApp;
