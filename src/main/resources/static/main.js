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

            messages.forEach(message => {
                const senderId = message.senderId;
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
            });
        })
        .catch(error => console.error('Ошибка при получении сообщений:', error));
}

// Создаем новую вкладку для пользователя
function createNewTab(senderId, tabContainer) {
    fetch('/getSenderName?senderId=' + senderId)
        .then(response => response.text())
        .then(userName => {
            const newTab = document.createElement('button');
            newTab.innerText = 'Пользователь ' + userName;
            newTab.classList.add('userTab');

            newTab.onclick = function() {
                switchChat(senderId); // Передаем senderId в switchChat
            };
            tabContainer.appendChild(newTab);
        })
        .catch(error => {
            console.error('Error fetching user name for tab:', error);
        });
}

// Функция для переключения чата
function switchChat(senderId) {
    // Скрываем все чаты
    document.querySelectorAll('.userMessageBox').forEach(box => {
        box.style.display = 'none';
    });

    // Показываем выбранный чат
    const selectedChat = document.getElementById('userChat-' + senderId);
    if (selectedChat) {
        selectedChat.style.display = 'block';
    }

    // Обновляем значение senderId в скрытом поле формы
    const senderIdInput = document.getElementById('senderIdInput');
    senderIdInput.value = senderId;

    // Показываем форму отправки сообщения
    const messageForm = document.getElementById('messageForm');
    messageForm.style.display = 'flex';
}

// Создаем новый чат
function createNewChat(senderId) {
    const messageBox = document.createElement('div');
    messageBox.id = 'userChat-' + senderId;
    messageBox.classList.add('userMessageBox');
    messageBox.style.display = 'none';

    const chatHeader = document.createElement('h2');
    chatHeader.innerText = 'Чат с пользователем: ' + senderId;

    const messageContainer = document.createElement('div');
    messageContainer.classList.add('messageContentContainer');

    // Добавляем заголовок и контейнер с сообщениями
    messageBox.appendChild(chatHeader);
    messageBox.appendChild(messageContainer);

    document.getElementById('userChatContainer').appendChild(messageBox);
}

// Старт опроса сообщений
function startPolling() {
    fetchMessages();
    setInterval(fetchMessages, 2000);
}

// Инициализация
window.onload = function() {
    startPolling();
};
