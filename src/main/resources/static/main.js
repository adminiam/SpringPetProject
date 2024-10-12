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

function fetchMessages() {
    fetch('/getMessage')
        .then(response => response.json())
        .then(messages => {
            const messageContainer = document.getElementById('messageContainer');
            messageContainer.innerHTML = '';
            messages.forEach(message => {
                const newMessage = document.createElement('p');
                newMessage.innerText = message;
                messageContainer.appendChild(newMessage);
            });
        })
        .catch(error => console.error('Ошибка при получении сообщений:', error));
}
function startPolling() {
    fetchMessages();
    setInterval(fetchMessages, 2000);
}

// Запуск при открытии страницы
window.onload = function() {
    startPolling();
};
