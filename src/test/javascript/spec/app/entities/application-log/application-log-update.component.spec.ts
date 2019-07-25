/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SeatAllotmentTestModule } from '../../../test.module';
import { ApplicationLogUpdateComponent } from 'app/entities/application-log/application-log-update.component';
import { ApplicationLogService } from 'app/entities/application-log/application-log.service';
import { ApplicationLog } from 'app/shared/model/application-log.model';

describe('Component Tests', () => {
  describe('ApplicationLog Management Update Component', () => {
    let comp: ApplicationLogUpdateComponent;
    let fixture: ComponentFixture<ApplicationLogUpdateComponent>;
    let service: ApplicationLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [ApplicationLogUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApplicationLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationLogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationLogService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationLog('9fec3727-3421-4967-b213-ba36557ca194');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationLog();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
