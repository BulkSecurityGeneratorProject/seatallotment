/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeatAllotmentTestModule } from '../../../test.module';
import { SpecialityDetailComponent } from 'app/entities/speciality/speciality-detail.component';
import { Speciality } from 'app/shared/model/speciality.model';

describe('Component Tests', () => {
  describe('Speciality Management Detail Component', () => {
    let comp: SpecialityDetailComponent;
    let fixture: ComponentFixture<SpecialityDetailComponent>;
    const route = ({ data: of({ speciality: new Speciality('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [SpecialityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpecialityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.speciality).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
  });
});
