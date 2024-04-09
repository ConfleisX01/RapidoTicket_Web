export async function registrarUsuario() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/registrarEmpleado'
    let usuario = document.getElementById('txtEmpleadoRegistro').value
    let contrasenia = document.getElementById('txtEmpleadoContrasenia').value
    let numeroEmpleado = document.getElementById('txtNumeroEmpleado').value

    if (verificarInputs()) {
        const formData = new URLSearchParams()
        formData.append('usuario', usuario)
        formData.append('contrasenia', contrasenia)
        formData.append('numeroEmpleado', numeroEmpleado)

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
                    title: "Registro completado",
                    showConfirmButton: false,
                    timer: 1500
                });
                fixSwal()
            } else {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Error al registrarse",
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
}

function verificarInputs() {
    let usuario = document.getElementById('txtEmpleadoRegistro').value
    let contrasenia = document.getElementById('txtEmpleadoContrasenia').value
    let numeroEmpleado = document.getElementById('txtNumeroEmpleado').value

    if (usuario == "") {
        return false
    }

    if (contrasenia == "") {
        return false
    }

    if (numeroEmpleado == "") {
        return false
    }

    return true
}

function fixSwal() {
    document.body.classList.remove('swal2-height-auto')
}