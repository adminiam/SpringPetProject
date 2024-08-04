function openModal() {
    const modal = document.getElementById("myModal");
    modal.style.display = "block";
    setTimeout(() => {
        modal.classList.add("show");
    }, 10);
}

function closeModal(event) {
    const modal = document.getElementById("myModal");
    if (event.target === modal || event.target.classList.contains('close')) {
        modal.classList.remove("show");
        setTimeout(() => {
            modal.style.display = "none";
        }, 500);
    }
}

window.onclick = function(event) {
    const modal = document.getElementById("myModal");
    if (event.target === modal) {
        closeModal(event);
    }
}

window.onload = function() {
    const modal = document.getElementById("myModal");
    modal.style.display = "none"; // Изначально скрыть модальное окно
}


function openModal1() {
    document.getElementById("myModal1").style.display = "block";
}

function closeModal1() {
    document.getElementById("myModal1").style.display = "none";
}


window.onclick = function(event) {
    var modal = document.getElementById("myModal1");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}


