// Importamos todas las funciones del controlador de cuentas
import * as ca from './ControllerAccount.js'
import * as cv from './comenzarViaje.js'
import * as cr from './enviarReporte.js'

// Funcion para crear los controladores y los botones para cambiar el contenido de la pagina
function startControllers() {
    let btnIndex = document.getElementById('btnIndex')
    let btnLogin = document.getElementById('btnLogin')
    let btnCode = document.getElementById('btnCode')
    let btnReport = document.getElementById('btnReport')
    let btnSalir = document.getElementById('btnSalir')

    btnIndex.addEventListener('click', () => {
        getModuleHome()
    })

    btnLogin.addEventListener('click', () => {
        getAccountContent()
    })

    btnCode.addEventListener('click', () => {
        getModuleCode()
    })

    btnReport.addEventListener('click', () => {
        getModuleReport()
    })

    btnSalir.addEventListener('click', () => {
        localStorage.clear()
        getModuleHome()
    })

    empleadoLogueado()
}

// Funcion para obtener el contenido del modulo home y aplicarlo
async function getModuleHome() {
    let content = await getModule('./html/home.html')
    applyContent(content)
}

// Funcion para obtener el contenido de los modulos Login y Crear Cuenta
async function getAccountContent() {
    let loginContent = await getModule('./html/login.html')
    let createContent = await getModule('./html/register.html')

    // Aplicamos el contenido de manera que los dos esten disponibles al mismo tiempo para solamente ocultarlos con las propiedades de css
    let container = document.getElementById('dinamic-container')
    container.innerHTML = loginContent + createContent;

    // Ocultamos los dos cards para evitar que se encimen y no se vean bien
    document.getElementById('login-card').style.display = 'none';
    document.getElementById('register-card').style.display = 'none';

    // Mostramos solamente el card de Login ya que el boton dice login
    document.getElementById('login-card').style.display = 'flex'

    // Colocamos los botones para poder modificar el contenido o cambiar entre los modulos
    let btnLogin = document.getElementById('btnLoginAccount')
    let btnCreate = document.getElementById('btnCreateAccount')

    // Mostramos el modulo Registrar y ocultamos el modulo Login
    btnCreate.addEventListener('click', () => {
        document.getElementById('login-card').style.display = 'none'
        document.getElementById('register-card').style.display = 'flex'
    })

    // Lo contrario al metodo anterior
    btnLogin.addEventListener('click', () => {
        document.getElementById('register-card').style.display = 'none'
        document.getElementById('login-card').style.display = 'flex'
    })

    let btnContinueLogin = document.getElementById('btnContinueLogin')
    
    ca.verificarLogin()

    btnContinueLogin.addEventListener('click', () => {
        ca.loginEmpleado()
        //window.location.href = './html/admin/index.html';
    })

    let btnContinueCreate = document.getElementById('btnContinueCreate')

    btnContinueCreate.addEventListener('click', () => {
        ca.registrarUsuario()
    })
}

// Funcion para obtener el modulo de ingresar codigo para comenzar el viaje
async function getModuleCode() {
    let content = await getModule('./html/code.html')

    ca.verificarLogin()

    //Agregamos el contenido
    document.getElementById('dinamic-container').innerHTML = content

    // Cargamos los controles
    let btnComenzarViaje = document.getElementById('btnComenzarViaje')

    btnComenzarViaje.addEventListener('click', () => {
        cv.comenzarViaje()
    })
    
    cv.VerificarViaje()
}

// Funcion para aplicar el contenido de los modulos en el contenedor principal
function applyContent(content) {
    let container = document.getElementById('dinamic-container')
    container.innerHTML = content
}

// Funcion para aplicar el contenido del modulo reporte
async function getModuleReport() {
    let content = await getModule('./html/report.html');
    applyContent(content)

    ca.verificarLogin()

    let btnSendMessaje = document.getElementById('btnSendMessage')

    btnSendMessaje.addEventListener('click', () => {
        cr.enviarReporte()
    })
}

// Funcion para obtener el html de los modulos pasando la url como parametro
async function getModule(url) {
    try {
        const response = await fetch(url)
        const html = await response.text()
        return html
    } catch (error) {
        return null
    }
}

// Funcion de testeo de los modulos
function testModuleContent(url) {
    getModuleContent(url)
}

// Funcion para resolver el error cuando se muestra una alerta LLAMAR CADA VEZ QUE SE MUESTRE UNA ALERTA
function fixSwal() {
    document.body.classList.remove('swal2-height-auto');
}

function empleadoLogueado() {
    let token = localStorage.getItem('token')
    let btnSalir = document.getElementById('btnSalir')

    if (token) {
        btnSalir.classList.remove("d-none");
    } else {
        btnSalir.classList.add("d-none");
    }
}

// Iniciamos los botones y les agregamos sus respectivas funciones
startControllers()
//testModuleContent("./html/report.html")
