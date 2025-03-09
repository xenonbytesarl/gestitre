package cm.xenonbyte.gestitre.application.stock;

import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewRequest;
import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewResponse;
import cm.xenonbyte.gestitre.application.stock.dto.FindStockMovePageInfoViewResponse;
import cm.xenonbyte.gestitre.application.stock.dto.SearchStockMovePageInfoViewResponse;
import cm.xenonbyte.gestitre.application.stock.dto.SearchStockMovesViewResponse;
import cm.xenonbyte.gestitre.application.stock.dto.StockMoveLineView;
import cm.xenonbyte.gestitre.application.stock.dto.UpdateStockMoveViewRequest;
import cm.xenonbyte.gestitre.application.stock.dto.UpdateStockMoveViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMoveLine;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface StockMoveViewMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "companyName", target = "companyName.text.value")
    @Mapping(source = "isinCode", target = "isinCode.text.value")
    @Mapping(source = "reference", target = "reference.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockNature.valueOf(createStockMoveViewRequest.getNature().name()))", target = "nature")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveType.valueOf(createStockMoveViewRequest.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveState.valueOf(createStockMoveViewRequest.getState().name()))", target = "state")
    @Mapping(source = "quantityCredit", target = "quantityCredit.value")
    @Mapping(source = "quantityDebit", target = "quantityDebit.value")
    @Mapping(source = "filename", target = "filename.text.value")
    @Mapping(expression = "java(createStockMoveViewRequest.getObservation() == null || createStockMoveViewRequest.getObservation().isEmpty()? null: cm.xenonbyte.gestitre.domain.stock.vo.Observation.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createStockMoveViewRequest.getObservation())))", target = "observation")
    @Mapping(expression = "java(new cm.xenonbyte.gestitre.domain.common.vo.CompanyId(createStockMoveViewRequest.getCompanyId()))", target = "companyId")
    @Mapping(source = "moveLines", qualifiedByName = "moveLineViewToMoveLines", target = "moveLines")
    @Nonnull StockMove toStockMove(@Nonnull @Valid CreateStockMoveViewRequest createStockMoveViewRequest);

    @Named("moveLineViewToMoveLines")
    List<StockMoveLine> moveLineViewToMoveLines(List<StockMoveLineView> moveLineViews);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "accountNumber", target = "accountNumber.text.value")
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveLineType.valueOf(moveLineView.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveLineState.valueOf(moveLineView.getState().name()))", target = "state")
    @Mapping(source = "quantity", target = "quantity.value")
    @Mapping(expression = "java(moveLineView.getCity() == null || moveLineView.getCity().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.City.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(moveLineView.getCity())))", target = "city")
    @Mapping(expression = "java(moveLineView.getZipCode() == null || moveLineView.getZipCode().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.ZipCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(moveLineView.getZipCode())))", target = "zipCode")
    @Mapping(expression = "java(moveLineView.getAdministrator() == null || moveLineView.getAdministrator().isEmpty()? null: cm.xenonbyte.gestitre.domain.shareholder.vo.Administrator.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(moveLineView.getAdministrator())))", target = "administrator")
    @Mapping(expression = "java(new cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderId(moveLineView.getShareHolderId()))", target = "shareHolderId")
    @Nonnull StockMoveLine moveLineViewToMoveLines(@Nonnull StockMoveLineView moveLineView);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "companyName.text.value", target = "companyName")
    @Mapping(source = "isinCode.text.value", target = "isinCode")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockNatureView.valueOf(stockMove.getNature().name()))", target = "nature")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveTypeView.valueOf(stockMove.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveStateView.valueOf(stockMove.getState().name()))", target = "state")
    @Mapping(source = "quantityCredit.value", target = "quantityCredit")
    @Mapping(source = "quantityDebit.value", target = "quantityDebit")
    @Mapping(source = "filename.text.value", target = "filename")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(expression = "java(stockMove.getObservation() == null || stockMove.getObservation().text().value().isEmpty()? null: stockMove.getObservation().text().value())", target = "observation")
    @Mapping(source = "reference.text.value", target = "reference")
    @Mapping(source = "companyId.value", target = "companyId")
    @Mapping(source = "tenantId.value", target = "tenantId")
    @Mapping(source = "moveLines", qualifiedByName = "moveLinesToMoveLineViews", target = "moveLines")
    @Nonnull @Valid CreateStockMoveViewResponse toCreateStockMoveViewResponse(@Nonnull StockMove stockMove);

    @Named("moveLinesToMoveLineViews")
    List<StockMoveLineView> moveLinesToMoveLineViews(List<StockMoveLine> moveLines);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "accountNumber.text.value", target = "accountNumber")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "reference.text.value", target = "reference")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveLineTypeView.valueOf(stockMoveLine.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveLineStateView.valueOf(stockMoveLine.getState().name()))", target = "state")
    @Mapping(source = "quantity.value", target = "quantity")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(expression = "java(stockMoveLine.getCity() == null || stockMoveLine.getCity().text().value().isEmpty()? null: stockMoveLine.getCity().text().value())", target = "city")
    @Mapping(expression = "java(stockMoveLine.getZipCode() == null || stockMoveLine.getZipCode().text().value().isEmpty()? null: stockMoveLine.getZipCode().text().value())", target = "zipCode")
    @Mapping(expression = "java(stockMoveLine.getAdministrator() == null || stockMoveLine.getAdministrator().text().value().isEmpty()? null: stockMoveLine.getAdministrator().text().value())", target = "administrator")
    @Mapping(source = "shareHolderId.value", target = "shareHolderId")
    @Mapping(source = "stockMoveId.value", target = "stockMoveId")
    @Mapping(source = "tenantId.value", target = "tenantId")
    @Nonnull StockMoveLineView moveLinesToMoveLineView(@Nonnull StockMoveLine stockMoveLine);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toSearchStockMovesViewResponses", target = "elements")
    @Nonnull @Valid SearchStockMovePageInfoViewResponse toSearchStockMovePageInfoViewResponse(@Nonnull PageInfo<StockMove> stockMovePageInfo);

    @Named("toSearchStockMovesViewResponses")
    @Nonnull List<SearchStockMovesViewResponse> toSearchStockMovePageInfoViewResponse(List<StockMove> stockMoves);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "companyName.text.value", target = "companyName")
    @Mapping(source = "isinCode.text.value", target = "isinCode")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockNatureView.valueOf(stockMove.getNature().name()))", target = "nature")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveTypeView.valueOf(stockMove.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveStateView.valueOf(stockMove.getState().name()))", target = "state")
    @Mapping(source = "quantityCredit.value", target = "quantityCredit")
    @Mapping(source = "quantityDebit.value", target = "quantityDebit")
    @Mapping(source = "filename.text.value", target = "filename")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(expression = "java(stockMove.getObservation() == null || stockMove.getObservation().text().value().isEmpty()? null: stockMove.getObservation().text().value())", target = "observation")
    @Mapping(source = "reference.text.value", target = "reference")
    @Mapping(source = "companyId.value", target = "companyId")
    @Mapping(source = "tenantId.value", target = "tenantId")
    @Nonnull @Valid SearchStockMovesViewResponse toSearchStockMovePageInfoViewResponse(StockMove stockMove);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "companyName.text.value", target = "companyName")
    @Mapping(source = "isinCode.text.value", target = "isinCode")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockNatureView.valueOf(stockMove.getNature().name()))", target = "nature")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveTypeView.valueOf(stockMove.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveStateView.valueOf(stockMove.getState().name()))", target = "state")
    @Mapping(source = "quantityCredit.value", target = "quantityCredit")
    @Mapping(source = "quantityDebit.value", target = "quantityDebit")
    @Mapping(source = "filename.text.value", target = "filename")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(expression = "java(stockMove.getObservation() == null || stockMove.getObservation().text().value().isEmpty()? null: stockMove.getObservation().text().value())", target = "observation")
    @Mapping(source = "reference.text.value", target = "reference")
    @Mapping(source = "companyId.value", target = "companyId")
    @Mapping(source = "tenantId.value", target = "tenantId")
    @Mapping(source = "moveLines", qualifiedByName = "moveLinesToMoveLineViews", target = "moveLines")
    @Nonnull FindStockMovePageInfoViewResponse toFindStockMovePageInfoViewResponse(@Nonnull StockMove stockMove);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "companyName", target = "companyName.text.value")
    @Mapping(source = "isinCode", target = "isinCode.text.value")
    @Mapping(source = "reference", target = "reference.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockNature.valueOf(updateStockMoveViewRequest.getNature().name()))", target = "nature")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveType.valueOf(updateStockMoveViewRequest.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.stock.vo.StockMoveState.valueOf(updateStockMoveViewRequest.getState().name()))", target = "state")
    @Mapping(source = "quantityCredit", target = "quantityCredit.value")
    @Mapping(source = "quantityDebit", target = "quantityDebit.value")
    @Mapping(source = "filename", target = "filename.text.value")
    @Mapping(expression = "java(updateStockMoveViewRequest.getObservation() == null || updateStockMoveViewRequest.getObservation().isEmpty()? null: cm.xenonbyte.gestitre.domain.stock.vo.Observation.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateStockMoveViewRequest.getObservation())))", target = "observation")
    @Mapping(expression = "java(new cm.xenonbyte.gestitre.domain.common.vo.CompanyId(updateStockMoveViewRequest.getCompanyId()))", target = "companyId")
    @Mapping(source = "tenantId", target = "tenantId.value")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "moveLines", qualifiedByName = "moveLineViewToMoveLines", target = "moveLines")
    @Nonnull StockMove toStockMove(UpdateStockMoveViewRequest updateStockMoveViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "companyName.text.value", target = "companyName")
    @Mapping(source = "isinCode.text.value", target = "isinCode")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockNatureView.valueOf(stockMove.getNature().name()))", target = "nature")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveTypeView.valueOf(stockMove.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.stock.dto.StockMoveStateView.valueOf(stockMove.getState().name()))", target = "state")
    @Mapping(source = "quantityCredit.value", target = "quantityCredit")
    @Mapping(source = "quantityDebit.value", target = "quantityDebit")
    @Mapping(source = "filename.text.value", target = "filename")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(expression = "java(stockMove.getObservation() == null || stockMove.getObservation().text().value().isEmpty()? null: stockMove.getObservation().text().value())", target = "observation")
    @Mapping(source = "reference.text.value", target = "reference")
    @Mapping(source = "companyId.value", target = "companyId")
    @Mapping(source = "tenantId.value", target = "tenantId")
    @Mapping(source = "moveLines", qualifiedByName = "moveLinesToMoveLineViews", target = "moveLines")
    @Nonnull @Valid UpdateStockMoveViewResponse toUpdateStockMoveViewResponse(@Nonnull StockMove stockMove);
}
