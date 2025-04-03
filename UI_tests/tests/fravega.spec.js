const {test,expect} = require('@playwright/test');
const { HomePage } = require('../pages/HomPage');
const { ProductPAge } = require('../pages/ProductPage');
const {CartPage} = require('../pages/CartPage');

test.only('Caso de Uso 1 - Buscar Heladera Samsung y agregar al carrito', async ({ page }) => {
    const home = new HomePage(page);
    const producto = new ProductPAge(page)
    const carrito = new CartPage(page);

    await home.goto();
    await home.cerrarModalCodigoPostalSiEstaVisible();
    await home.buscarProducto("Heladera Samsung",1);
    await producto.comprarProducto();
    await carrito.validarProductoVisible();
});

test('Caso de Uso 2 - Buscar Heladera Samsung, agregarlo al carrito y despues quitarlo', async ({ page }) => {
    const home = new HomePage(page);
    const producto = new ProductPAge(page)
    const carrito = new CartPage(page);

    await home.goto();
    await home.cerrarModalCodigoPostalSiEstaVisible();
    await home.buscarProducto("Heladera Samsung",1);
    await producto.comprarProducto();
    await carrito.validarProductoVisible();
    await carrito.eliminarProducto()
    await carrito.validarQueProductoNoExiste()
});