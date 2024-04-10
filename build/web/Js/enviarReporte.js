export function enviarReporte() {
    let empleado = localStorage.getItem('idEmpleado')
    let txtEmpId = document.getElementById('txtEmpId')
    let txtEmpProblem = document.getElementById('txtEmpProblem').value

    if (empleado) {
        txtEmpId.value = empleado
        if (validarInputs()) {
            enviarMensaje(txtEmpProblem)
        } else {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Ingresa el mensaje para poder enviar el reporte",
            });
            fixSwal()
        }
    } else {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Tienes que iniciar sesion para poder enviar un reporte",
        });
        fixSwal()
    }
}

function validarInputs() {
    let txtEmpProblem = document.getElementById('txtEmpProblem').value

    if (txtEmpProblem == "") {
        return false
    }

    return true
}

function fixSwal() {
    document.body.classList.remove('swal2-height-auto')
}

async function enviarMensaje(mensaje) {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/mensajes/agregarMensaje'
    let idEmpleado = localStorage.getItem('idEmpleado')

    const formData = new URLSearchParams()
    formData.append('idEmpleado', idEmpleado)
    formData.append('mensaje', mensaje)

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    }

    try {
        const response = await fetch(URL, requestOptions)
        if (response.ok) {
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: "Mensaje agregado",
                showConfirmButton: false,
                timer: 1500
            });
            fixSwal()
        } else {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Error al agregar el mensaje",
            });
            fixSwal()
        }
    } catch (error) {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: `${error.message}`,
        });
        fixSwal()
    }
}