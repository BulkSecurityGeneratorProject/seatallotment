/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeatAllotmentTestModule } from '../../../test.module';
import { ApplicationlogDetailComponent } from 'app/entities/applicationlog/applicationlog-detail.component';
import { Applicationlog } from 'app/shared/model/applicationlog.model';

describe('Component Tests', () => {
  describe('Applicationlog Management Detail Component', () => {
    let comp: ApplicationlogDetailComponent;
    let fixture: ComponentFixture<ApplicationlogDetailComponent>;
    const route = ({ data: of({ applicationlog: new Applicationlog('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [ApplicationlogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApplicationlogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationlogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationlog).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
  });
});
