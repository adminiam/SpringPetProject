function openModal() {
    const modal = document.getElementById("myModal");
    modal.style.display = "block";
    setTimeout(() => {
        modal.classList.add("show");
        modal.classList.remove("hide");
    }, 10);
}

function closeModal(event) {
    const modal = document.getElementById("myModal");
    if (event.target === modal || event.target.classList.contains('close')) {
        modal.classList.add("hide");
        modal.classList.remove("show");
        setTimeout(() => {
            modal.style.display = "none";
        }, 500);
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



