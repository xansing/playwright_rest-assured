exports.HomePage = class homePage {
    constructor(page){
        this.page = page;
        this.ingresarCodigoPostal = page.locator('[data-test-id="geo-modal-wrapper"]');
        this.cerrarModalCodigoPostal = page.locator('[data-test-id="close-modal-button"]');
        this.barraDeBusqueda = page.locator('input[placeholder= "Buscar productos"]');
        this.recomendacionesDeBusqueda = page.locator('xpath=//*[@data-test-id="autosuggest"]/a[@data-position="1"]');
    }

    async goto(){
        await this.page.goto('https://www.fravega.com/');
    }

    async cerrarModalCodigoPostalSiEstaVisible() {
        if(await this.ingresarCodigoPostal.isVisible()){
            await this.cerrarModalCodigoPostal.click();
        }
    }

    async buscarProducto(nombreProducto, orden = 0){
        await this.barraDeBusqueda.fill(nombreProducto);
        await this.barraDeBusqueda.click();
        const recomendacionesDeBusqueda = this.page.locator(`xpath=//*[@data-test-id="autosuggest"]/a[@data-position="${orden}"]`)
        await recomendacionesDeBusqueda.click();
    }

}