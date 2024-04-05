function cargarControles() {
    let btnAgregarCamion = document.getElementById('btnAgregarCamion');

    btnAgregarCamion.addEventListener('click', () => {
        agregarCamion();
    })
}

function agregarCamion() {
    fetch('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/agregarCamion')
    .then(response => {
      if (!response.ok) {
        throw new Error('Hubo un problema al llamar a la API.');
      }
      return response.json();
    })
    .then(data => {
      // Mostrar el mensaje devuelto en un alert
      alert(data.response);
    })
    .catch(error => {
      // Manejar errores
      alert('Error: ' + error.response);
    });
}

function cargarTablaCamiones() {
    
}

cargarControles();