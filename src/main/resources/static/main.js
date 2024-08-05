function openModal() {
    const modal = document.getElementById("myModal");
    modal.style.display = "block";
    setTimeout(() => {
        modal.classList.add("show");
    }, 10);
}

function closeModal(event) {
    const modal = document.getElementById("myModal");
    // Закрыть модальное окно, если клик был на область вне модального окна или на кнопку закрытия
    if (event.target === modal || event.target.classList.contains('close')) {
        modal.classList.remove("show");
        setTimeout(() => {
            modal.style.display = "none";
        });
    }
}

window.onclick = function (event) {
    const modal = document.getElementById("myModal");
    if (event.target === modal) {
        closeModal(event);
    }
}

window.onload = function () {
    const modal = document.getElementById("myModal");
    modal.style.display = "none";
}


function openModal1() {
    document.getElementById("myModal1").style.display = "block";
}

function closeModal1() {
    document.getElementById("myModal1").style.display = "none";
}


window.onclick = function (event) {
    var modal = document.getElementById("myModal1");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const errorMessageElement = document.querySelector('[data-message="Invalid username and password."]');
    const logoutMessageElement = document.querySelector('[data-message="You have been logged out."]');

    if (errorMessageElement) {
        showNotification(errorMessageElement.getAttribute('data-message'));
    }
    if (logoutMessageElement) {
        showNotification(logoutMessageElement.getAttribute('data-message'));
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



