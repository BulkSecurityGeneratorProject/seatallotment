import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SeatAllotmentSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [SeatAllotmentSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SeatAllotmentSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatAllotmentSharedModule {
  static forRoot() {
    return {
      ngModule: SeatAllotmentSharedModule
    };
  }
}
