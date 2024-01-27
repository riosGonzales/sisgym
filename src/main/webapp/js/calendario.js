const daysTag = document.querySelector(".days");
const currentDate = document.querySelector(".current-date");
const prevNextIcon = document.querySelectorAll(".icons span");
const calendarDays = document.querySelectorAll(".days li");
const cancelModal = document.getElementById("cancelModal");
const closeModalButton = document.getElementById("closeModal");
const cancelButton = document.querySelector("button.btn-secondary");

let date = new Date();
let currYear = date.getFullYear();
let currMonth = date.getMonth();

const months = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

const renderCalendar = () => {
    let firstDayofMonth = new Date(currYear, currMonth, 1).getDay();
    let lastDateofMonth = new Date(currYear, currMonth + 1, 0).getDate();
    let lastDayofMonth = new Date(currYear, currMonth, lastDateofMonth).getDay();
    let lastDateofLastMonth = new Date(currYear, currMonth, 0).getDate();
    let liTag = "";

    for (let i = firstDayofMonth; i > 0; i--) {
        liTag += `<li class="inactive">${lastDateofLastMonth - i + 1}</li>`;
    }

    for (let i = 1; i <= lastDateofMonth; i++) {
        let isToday = i === date.getDate() && currMonth === date.getMonth() && currYear === date.getFullYear() ? "active" : "";
        liTag += `<li class="${isToday}">${i}</li>`;
    }

    for (let i = lastDayofMonth; i < 6; i++) {
        liTag += `<li class="inactive">${i - lastDayofMonth + 1}</li>`;
    }
    currentDate.innerText = `${months[currMonth]} ${currYear}`;
    daysTag.innerHTML = liTag;

    // Agregar un manejador de eventos al contenedor de días del calendario
    daysTag.addEventListener("click", (event) => {
        if (event.target.tagName === "LI" && !event.target.classList.contains("inactive")) {
            const dayClicked = parseInt(event.target.innerText); // Día en el que se hizo clic
            const newDate = new Date(currYear, currMonth, dayClicked);
            const formattedDate = newDate.toLocaleDateString("es-ES", {
                year: "numeric",
                month: "2-digit",
                day: "2-digit"
            });

            // Almacena la fecha en sessionStorage
            sessionStorage.setItem("fechaCalendario", formattedDate);
            openModal();
        }
    });
};

renderCalendar();

prevNextIcon.forEach(icon => {
    icon.addEventListener("click", () => {
        currMonth = icon.id === "prev" ? currMonth - 1 : currMonth + 1;

        if (currMonth < 0) {
            currYear--;
            currMonth = 11;
        } else if (currMonth > 11) {
            currYear++;
            currMonth = 0;
        }
        renderCalendar();
    });
});

// Función para mostrar el modal
function openModal() {
    cancelModal.style.display = "block";
}

// Función para ocultar el modal
function closeModal() {
    cancelModal.style.display = "none";
}

closeModalButton.addEventListener("click", closeModal);

cancelButton.addEventListener("click", () => {
    closeModal();
});

window.addEventListener("click", (event) => {
    if (event.target === cancelModal) {
        closeModal();
    }
});
var logi = sessionStorage.getItem("logi");

const verHorariosButton = document.getElementById("btnVerHorarios");
verHorariosButton.addEventListener("click", () => {
    let url = "clases.html?logi=" + logi;
   $(location).attr('href', url);

});
document.body.appendChild(cancelModal);
