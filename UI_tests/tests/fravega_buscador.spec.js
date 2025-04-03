const { test, expect } = require('@playwright/test');
test('Caso de Uso 1 - Buscar Heladera Samsung y agregar al carrito', async ({ page }) => {
    await page.goto('https://www.fravega.com/');
    const ingresarCodigoPostal = page.locator('[data-test-id="geo-modal-wrapper"]');
    if (await ingresarCodigoPostal.isVisible()) {
        const cerrarModalCodigoPostal = page.locator('[data-test-id="close-modal-button"]')
        await cerrarModalCodigoPostal.click();
    }

    const barraDeBusqueda = await page.locator('input[placeholder= "Buscar productos"]');
    await barraDeBusqueda.fill('Heladera Samsung');
    await barraDeBusqueda.click();
    const recomendacionesDeBusqueda = await page.waitForSelector('xpath=//*[@data-test-id="autosuggest"]/a[@data-position="1"]');
    await recomendacionesDeBusqueda.click()

    const botonComprar = await page.locator("(//div[@data-test-id='product-info']//button[contains(text(), 'Comprar')])[2]")
    await botonComprar.click()
    await page.waitForURL('**/chk-ui**');
    await expect(page.locator('text=Heladera Samsung No Frost Inverter RT29K577JS8 308Lts')).toBeVisible();

});

test('Caso de Uso 2 - Buscar Heladera Samsung, agregarlo al carrito y despues quitarlo', async ({ page }) => {
    await page.goto('https://www.fravega.com/');
    const ingresarCodigoPostal = page.locator('[data-test-id="geo-modal-wrapper"]');
    if (await ingresarCodigoPostal.isVisible()) {
        const cerrarModalCodigoPostal = page.locator('[data-test-id="close-modal-button"]')
        await cerrarModalCodigoPostal.click();
    }

    const barraDeBusqueda = await page.locator('input[placeholder= "Buscar productos"]');
    await barraDeBusqueda.fill('Heladera Samsung');
    await barraDeBusqueda.click();
    const recomendacionesDeBusqueda = await page.waitForSelector('xpath=//*[@data-test-id="autosuggest"]/a[@data-position="1"]');
    await recomendacionesDeBusqueda.click()

    const botonComprar = await page.locator("(//div[@data-test-id='product-info']//button[contains(text(), 'Comprar')])[2]")
    await botonComprar.click()
    await page.waitForURL('**/chk-ui**');
    await expect(page.locator('text=Heladera Samsung No Frost Inverter RT29K577JS8 308Lts')).toBeVisible();
    const botonEliminar = await page.locator("//span[text() = 'Eliminar']");
    await botonEliminar.click();
    await expect(page.locator("//*[text() = '¿Estas seguro/a de querer eliminar?']")).toBeVisible();
    const botonConfirmarEliminar = await page.locator("//button[text() = 'Eliminar']");
    await botonConfirmarEliminar.click();
    await expect(page.locator('text=Heladera Samsung No Frost Inverter RT29K577JS8 308Lts')).toHaveCount(0);
    await expect(page.locator("//*[text() = 'Su carrito está vacio']")).toBeVisible();
});