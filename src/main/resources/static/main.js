function openModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.style.display = "block";
    setTimeout(() => {
        modal.classList.add("show");
        modal.classList.remove("hide");
    }, 10);
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.add("hide");
    modal.classList.remove("show");
    setTimeout(() => {
        modal.style.display = "none";
    }, 500);
}

window.onclick = function(event) {
    const modals = document.getElementsByClassName("modal");
    for (let i = 0; i < modals.length; i++) {
        if (event.target === modals[i]) {
            closeModal(modals[i].id);
        }
    }
}

window.onload = function() {
    const urlParams = new URLSearchParams(window.location.search);
    const modalOpen = urlParams.get('modalOpen');

    const modals = document.getElementsByClassName("modal");
    for (let i = 0; i < modals.length; i++) {
        modals[i].style.display = "none";
    }

    if (modalOpen) {
        openModal('myModal1');
    }
};

document.addEventListener('DOMContentLoaded', function () {
    const errorMessageElement = document.querySelector('[data-message="Invalid username and password."]');
    const logoutMessageElement = document.querySelector('[data-message="You have been logged out."]');
    const inputMessageElement = document.querySelector('[data-message="Fill in at least one field"]');

    if (errorMessageElement) {
        showNotification(errorMessageElement.getAttribute('data-message'));
    }
    if (logoutMessageElement) {
        showNotification(logoutMessageElement.getAttribute('data-message'));
    }
    if (inputMessageElement){
        showNotification(inputMessageElement.getAttribute('data-message'));
    }
});

function showNotification(message) {
    const notifications = document.getElementById('notifications');
    const notification = document.createElement('div');
    notification.classList.add('notification');
    notification.innerText = message;

    const timer = document.createElement('div');
    timer.classList.add('timer');
    notification.appendChild(timer);

    notifications.appendChild(notification);

    setTimeout(() => {
        notification.classList.add('slideOut');
    }, 5000);

    notification.addEventListener('animationend', (event) => {
        if (event.animationName === 'slideOut') {
            notification.remove();
        }
    });
}

function toggleDropdown() {
    var dropdownMenu = document.getElementById("dropdown-menu");
    if (dropdownMenu.style.display === "none" || dropdownMenu.style.display === "") {
        dropdownMenu.style.display = "block";
    } else {
        dropdownMenu.style.display = "none";
    }
}

    const displayedMessageIds = new Set();
    const users = new Set();

function fetchMessages() {
    fetch('/getMessage')
        .then(response => response.json())
        .then(messages => {
            const tabContainer = document.getElementById('userTabContainer');
            const chatContainer = document.getElementById('userChatContainer');

            // Итерируем по списку, а не по объекту
            messages.forEach(map => {
                // В каждой карте (Map) содержится один элемент с ключом и сообщением
                for (let key in map) {
                    const message = map[key];
                    const senderId = key;
                    const messageText = message.message;
                    const messageId = message.id;

                    if (!displayedMessageIds.has(messageId)) {
                        if (!users.has(senderId)) {
                            createNewTab(senderId, tabContainer);
                            users.add(senderId);
                        }

                        let messageBox = document.getElementById('userChat-' + senderId);
                        if (!messageBox) {
                            messageBox = createNewChat(senderId);
                            chatContainer.appendChild(messageBox);
                        }

                        const messageContainer = messageBox.querySelector('.messageContentContainer');
                        const newMessage = document.createElement('p');
                        newMessage.innerText = messageText;
                        messageContainer.appendChild(newMessage);

                        displayedMessageIds.add(messageId);
                    }
                }
            });
        })
        .catch(error => console.error('Ошибка при получении сообщений:', error));
}

document.getElementById('messageForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const messageInput = document.getElementById('messageInput');
    const messageText = messageInput.value.trim();
    const receiverId = document.getElementById('receiverIdInput').value;

    if (messageText !== '') {
        const formData = new FormData(this);

        try {
            const response = await fetch(this.action, {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                const messageBox = document.getElementById('userChat-' + receiverId);

                if (messageBox) {
                    const messageContainer = messageBox.querySelector('.messageContentContainer');
                    if (messageContainer) {
                        const messageDiv = document.createElement('p');
                        messageDiv.innerText = messageText;
                        messageContainer.scrollTop = messageContainer.scrollHeight;
                        messageInput.value = '';
                        console.log('Сообщение успешно добавлено в чат пользователя');
                    } else {
                        console.error('Контейнер сообщений не найден в чате');
                    }
                } else {
                    console.error('Чат пользователя не найден');
                }
            } else {
                console.error('Ошибка при отправке сообщения:', response.status);
            }
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }
});
function createNewTab(senderId, tabContainer) {
    fetch('/getSenderName?senderId=' + senderId)
        .then(response => response.text())
        .then(userName => {
            const newTab = document.createElement('button');
            newTab.innerText = 'Пользователь ' + userName;
            newTab.classList.add('userTab');

            newTab.onclick = function() {
                switchChat(senderId);
            };
            tabContainer.appendChild(newTab);
        })
        .catch(error => {
            console.error('Error fetching user name for tab:', error);
        });
}

function switchChat(senderId) {
    document.querySelectorAll('.userMessageBox').forEach(box => {
        box.style.display = 'none';
    });

    const selectedChat = document.getElementById('userChat-' + senderId);
    if (selectedChat) {
        selectedChat.style.display = 'block';
    }

    const senderIdInput = document.getElementById('senderIdInput');
    senderIdInput.value = senderId;

    const receiverIdInput = document.getElementById('receiverIdInput');
    receiverIdInput.value = senderId;

    const messageForm = document.getElementById('messageForm');
    messageForm.style.display = 'flex';
}

function createNewChat(senderId) {
    const messageBox = document.createElement('div');
    messageBox.id = 'userChat-' + senderId;
    messageBox.classList.add('userMessageBox');
    messageBox.style.display = 'none';

    const chatHeader = document.createElement('h2');
    chatHeader.innerText = 'Чат с пользователем: ' + senderId;

    const messageContainer = document.createElement('div');
    messageContainer.classList.add('messageContentContainer');

    messageBox.appendChild(chatHeader);
    messageBox.appendChild(messageContainer);

    document.getElementById('userChatContainer').appendChild(messageBox);
}

function startPolling() {
    fetchMessages();
    setInterval(fetchMessages, 1000);
}

window.onload = function() {
    startPolling();
};
