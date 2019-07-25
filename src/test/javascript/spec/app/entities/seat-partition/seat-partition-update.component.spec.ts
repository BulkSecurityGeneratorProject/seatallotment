/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SeatAllotmentTestModule } from '../../../test.module';
import { SeatPartitionUpdateComponent } from 'app/entities/seat-partition/seat-partition-update.component';
import { SeatPartitionService } from 'app/entities/seat-partition/seat-partition.service';
import { SeatPartition } from 'app/shared/model/seat-partition.model';

describe('Component Tests', () => {
  describe('SeatPartition Management Update Component', () => {
    let comp: SeatPartitionUpdateComponent;
    let fixture: ComponentFixture<SeatPartitionUpdateComponent>;
    let service: SeatPartitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [SeatPartitionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SeatPartitionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SeatPartitionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SeatPartitionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SeatPartition('9fec3727-3421-4967-b213-ba36557ca194');
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
        const entity = new SeatPartition();
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
