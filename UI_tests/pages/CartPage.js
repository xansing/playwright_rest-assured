const { expect } = require("@playwright/test");

exports.CartPage = class CartPage{
    constructor(page){
        this.page = page;
        this.botonEliminar = page.locator("//span[text() = 'Eliminar']");
        this.ModalConfirmacionParaEliminar = page.locator("//*[text() = '¿Estas seguro/a de querer eliminar?']");
        this.botonConfirmarEliminar = page.locator("//button[text() = 'Eliminar']");
        this.mensajeCarritoVacio = page.locator("//*[text() = 'Su carrito está vacio']");
    }

    async validarProductoVisible(){
        await expect(this.page.locator('text=Heladera Samsung No Frost Inverter RT29K577JS8 308Lts')).toBeVisible();
    }

    async eliminarProducto(){
        await this.botonEliminar.click();
        await expect(this.ModalConfirmacionParaEliminar).toBeVisible();
        await this.botonConfirmarEliminar.click()
    }

    async validarQueProductoNoExiste(){
        await expect(this.page.locator('text=Heladera Samsung No Frost Inverter RT29K577JS8 308Lts')).toHaveCount(0);
        await expect(this.mensajeCarritoVacio).toBeVisible();
    }
}