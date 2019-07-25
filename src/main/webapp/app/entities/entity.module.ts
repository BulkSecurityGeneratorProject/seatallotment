import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'district',
        loadChildren: './district/district.module#SeatAllotmentDistrictModule'
      },
      {
        path: 'speciality',
        loadChildren: './speciality/speciality.module#SeatAllotmentSpecialityModule'
      },
      {
        path: 'institute',
        loadChildren: './institute/institute.module#SeatAllotmentInstituteModule'
      },
      {
        path: 'seat-partition',
        loadChildren: './seat-partition/seat-partition.module#SeatAllotmentSeatPartitionModule'
      },
      {
        path: 'application',
        loadChildren: './application/application.module#SeatAllotmentApplicationModule'
      },
      {
        path: 'applicationlog',
        loadChildren: './applicationlog/applicationlog.module#SeatAllotmentApplicationlogModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatAllotmentEntityModule {}
