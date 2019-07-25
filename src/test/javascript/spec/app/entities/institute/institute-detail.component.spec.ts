/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeatAllotmentTestModule } from '../../../test.module';
import { InstituteDetailComponent } from 'app/entities/institute/institute-detail.component';
import { Institute } from 'app/shared/model/institute.model';

describe('Component Tests', () => {
  describe('Institute Management Detail Component', () => {
    let comp: InstituteDetailComponent;
    let fixture: ComponentFixture<InstituteDetailComponent>;
    const route = ({ data: of({ institute: new Institute('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [InstituteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InstituteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstituteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.institute).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
  });
});
