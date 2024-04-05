function cargarMenu() {
  let btnVistaGeneral = document.getElementById('btnVistaGeneral')
  let btnPanelCamiones = document.getElementById('btnPanelCamiones')
  btnVistaGeneral.addEventListener("click", () => {
    cargarVistaGeneral()
  })

  btnPanelCamiones.addEventListener('click', () => {
    cargarPanelCamiones()
  })
}

function cargarControles() {
  let btnAgregarCamion = document.getElementById('btnAgregarCamion');

  btnAgregarCamion.addEventListener('click', () => {
    agregarCamion()
  })
}

async function cargarVistaGeneral() {
  let contenedor = document.getElementById('main-container')

  try {
    const response = await fetch("./general.html")
    const html = await response.text()
    contenedor.innerHTML = ""
    contenedor.innerHTML = html
  } catch (error) {
    console.log(error)
  }
}

async function cargarPanelCamiones() {
  let contenedor = document.getElementById('main-container')

  try {
    const response = await fetch('./buses.html')
    const html = await response.text()
    contenedor.innerHTML = ""
    contenedor.innerHTML = html
    cargarControles()
  } catch (error) {
    console.log(error)
  }
}

function agregarCamion() {
  fetch('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/agregarCamion')
    .then(response => {
      if (!response.ok) {
        throw new Error('Hubo un problema al llamar a la API.');
      }
      return response.json()
    })
    .then(data => {
      Swal.fire({
        position: "top-end",
        icon: "success",
        title: `${data.response}`,
        showConfirmButton: false,
        timer: 1500
      });
      fixSwal()
    })
    .catch(error => {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: `${data.response}`,
      });
      fixSwal()
    });
}

function fixSwal() {
  document.body.classList.remove('swal2-height-auto');
}

cargarMenu()
cargarVistaGeneral()