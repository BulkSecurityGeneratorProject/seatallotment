/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeatAllotmentTestModule } from '../../../test.module';
import { ApplicationLogDetailComponent } from 'app/entities/application-log/application-log-detail.component';
import { ApplicationLog } from 'app/shared/model/application-log.model';

describe('Component Tests', () => {
  describe('ApplicationLog Management Detail Component', () => {
    let comp: ApplicationLogDetailComponent;
    let fixture: ComponentFixture<ApplicationLogDetailComponent>;
    const route = ({ data: of({ applicationLog: new ApplicationLog('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [ApplicationLogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApplicationLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationLog).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
  });
});
