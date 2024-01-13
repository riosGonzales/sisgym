# Uso

El sistema proporciona diversas funcionalidades, incluyendo:

### Gestión de Asistencias:

Proporciona un control de asistencia para el acceso de un cliente. En caso de que un cliente no esté matriculado o tenga una membresía caducada, se le impedirá el acceso.

### Informe de Análisis

Proporciona análisis detallados con gráficos estadísticos sobre:

1. **Ingresos Económicos Mensuales:**
   Genera informes mensuales que incluyen gráficos estadísticos para visualizar los ingresos económicos del gimnasio. Los formatos exportables incluyen CSV, PDF y Word.

2. **Cantidad de Asistencias Diarias:**
   Ofrece un análisis detallado sobre la cantidad de asistencias registradas cada día. Puedes exportar estos datos en formatos como CSV, PDF y Word.

3. **Membresía con Mayor Modalidad:**
   Realiza un análisis para identificar la membresía con la mayor modalidad de suscripción. Los resultados se presentan con gráficos y pueden ser exportados en formatos como CSV, PDF y Word.

Estos informes te proporcionarán una visión completa y detallada de la salud financiera del gimnasio, la asistencia diaria y las preferencias de membresía.

### Facturación y Pagos

El proceso de matrícula y pago de un cliente genera una factura completamente imprimible. La factura incluirá todos los detalles relevantes, proporcionando una documentación clara y detallada para los clientes y el gimnasio.

### Gestión de Matrículas:

La gestión de matrículas ofrece las siguientes funcionalidades:

1. **Registro de Clientes:**
   Permite registrar nuevos clientes, proporcionando la información necesaria para su membresía en el gimnasio.

2. **Edición de Clientes:**
   Ofrece la posibilidad de realizar ediciones en los registros de clientes activos e inactivos, actualizando la información según sea necesario.

3. **Eliminación de Clientes Activos:**
   Permite eliminar registros de clientes activos en caso de necesidad o cambio de estado.

4. **Renovación de Clientes Inactivos:**
   En el caso de que un cliente esté inactivo, se brinda la opción de renovar su membresía para reactivar su participación en el gimnasio.

Esta funcionalidad garantiza una gestión eficiente y flexible de las matrículas, adaptándose a las necesidades cambiantes de los clientes y del gimnasio.

### Login y Seguridad

El sistema de autenticación y seguridad ofrece las siguientes características:

1. **Login Validado por JWT:**
   Utiliza JSON Web Tokens (JWT) para la autenticación de usuarios, proporcionando un método seguro y eficiente para validar las credenciales de acceso.

2. **Sesiones Encriptadas:**
   Todas las sesiones de usuario se gestionan con encriptación para garantizar la seguridad de la información durante la transmisión y almacenamiento.

3. **Doble Autenticación:**
   Implementa un sistema de doble autenticación (2FA) para agregar una capa adicional de seguridad. Los usuarios deberán proporcionar un segundo factor (como un código generado en una aplicación de autenticación) además de sus credenciales habituales.
