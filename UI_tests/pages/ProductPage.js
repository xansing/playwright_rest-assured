exports.ProductPAge = class ProductPAge {
    constructor(page){
        this.page = page;
        this.botonComprar = page.locator("(//div[@data-test-id='product-info']//button[contains(text(), 'Comprar')])[2]")
    }

    async comprarProducto(){
        await this.botonComprar.click();
        await this.page.waitForURL('**/chk-ui**');
    }
}