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

export async function loginEmpleado() {
    let usuario = document.getElementById('txtUsuario').value
    let contrasenia = document.getElementById('txtContrasenia').value
    const URL = `http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/loginEmpleado?usuario=${usuario}&contrasenia=${contrasenia}`

    try {
        const response = await fetch(URL)
        const data = await response.json()
        console.log(data)
        if (data.response != "Error") {
            localStorage.setItem("token", data)
            verificarLogin()
        }
    } catch (error) {
        console.log(error)
    }
}

export async function verificarLogin() {
    let token = localStorage.getItem("token")

    if (token) {
        const URL = `http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/verificarLogin?token=${token}`
        try {
            const response = await fetch(URL)
            const data = await response.json()
            
            if (data != null && data.numeroEmpleado.startsWith("ADM")) {
                window.location.href = './html/admin/index.html';
            } else if (data != null && data.numeroEmpleado.startsWith("EMP")) {
                console.log("Empleado y no administrador")
                localStorage.setItem("numeroEmpleado", data.numeroEmpleado)
            }
            
        } catch (error) {
            console.log(error)
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