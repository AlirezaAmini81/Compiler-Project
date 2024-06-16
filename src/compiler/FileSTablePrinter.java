package compiler;

import gen.CListener;
import gen.CParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Map;

public class FileSTablePrinter implements CListener {
    ArrayList<SymbolTable> symbolTables;
    int nested = 0;
    SymbolTable parent;
    String intRegex = "\\d+";
    String doubleRegex = "\\d+(\\.\\d+)?";
    String charRegex = "'.'";
    
    public FileSTablePrinter(){
        symbolTables = new ArrayList<>();
    }

    @Override
    public void enterPrimaryExpression(CParser.PrimaryExpressionContext ctx) {
    }

    @Override
    public void exitPrimaryExpression(CParser.PrimaryExpressionContext ctx) {

    }

    @Override
    public void enterPostfixExpression(CParser.PostfixExpressionContext ctx) {

    }

    @Override
    public void exitPostfixExpression(CParser.PostfixExpressionContext ctx) {

    }

    @Override
    public void enterArgumentExpressionList(CParser.ArgumentExpressionListContext ctx) {

    }

    @Override
    public void exitArgumentExpressionList(CParser.ArgumentExpressionListContext ctx) {

    }

    @Override
    public void enterUnaryExpression(CParser.UnaryExpressionContext ctx) {
    }

    @Override
    public void exitUnaryExpression(CParser.UnaryExpressionContext ctx) {

    }

    @Override
    public void enterUnaryOperator(CParser.UnaryOperatorContext ctx) {

    }

    @Override
    public void exitUnaryOperator(CParser.UnaryOperatorContext ctx) {

    }

    @Override
    public void enterCastExpression(CParser.CastExpressionContext ctx) {

    }

    @Override
    public void exitCastExpression(CParser.CastExpressionContext ctx) {

    }

    @Override
    public void enterMultiplicativeExpression(CParser.MultiplicativeExpressionContext ctx) {

    }

    @Override
    public void exitMultiplicativeExpression(CParser.MultiplicativeExpressionContext ctx) {

    }

    @Override
    public void enterAdditiveExpression(CParser.AdditiveExpressionContext ctx) {

    }

    @Override
    public void exitAdditiveExpression(CParser.AdditiveExpressionContext ctx) {

    }

    @Override
    public void enterShiftExpression(CParser.ShiftExpressionContext ctx) {

    }

    @Override
    public void exitShiftExpression(CParser.ShiftExpressionContext ctx) {

    }

    @Override
    public void enterRelationalExpression(CParser.RelationalExpressionContext ctx) {

    }

    @Override
    public void exitRelationalExpression(CParser.RelationalExpressionContext ctx) {

    }

    @Override
    public void enterEqualityExpression(CParser.EqualityExpressionContext ctx) {

    }

    @Override
    public void exitEqualityExpression(CParser.EqualityExpressionContext ctx) {

    }

    @Override
    public void enterAndExpression(CParser.AndExpressionContext ctx) {

    }

    @Override
    public void exitAndExpression(CParser.AndExpressionContext ctx) {

    }

    @Override
    public void enterExclusiveOrExpression(CParser.ExclusiveOrExpressionContext ctx) {

    }

    @Override
    public void exitExclusiveOrExpression(CParser.ExclusiveOrExpressionContext ctx) {

    }

    @Override
    public void enterInclusiveOrExpression(CParser.InclusiveOrExpressionContext ctx) {

    }

    @Override
    public void exitInclusiveOrExpression(CParser.InclusiveOrExpressionContext ctx) {

    }

    @Override
    public void enterLogicalAndExpression(CParser.LogicalAndExpressionContext ctx) {

    }

    @Override
    public void exitLogicalAndExpression(CParser.LogicalAndExpressionContext ctx) {

    }

    @Override
    public void enterLogicalOrExpression(CParser.LogicalOrExpressionContext ctx) {

    }

    @Override
    public void exitLogicalOrExpression(CParser.LogicalOrExpressionContext ctx) {

    }

    @Override
    public void enterConditionalExpression(CParser.ConditionalExpressionContext ctx) {

    }

    @Override
    public void exitConditionalExpression(CParser.ConditionalExpressionContext ctx) {

    }

    @Override
    public void enterAssignmentExpression(CParser.AssignmentExpressionContext ctx) {
        try {
            String str1 = ctx.unaryExpression().getText();
            String str2 = ctx.assignmentExpression().conditionalExpression().logicalOrExpression().logicalAndExpression(0).inclusiveOrExpression(0)
                    .exclusiveOrExpression(0).andExpression(0).equalityExpression(0).relationalExpression(0).shiftExpression(0).additiveExpression(0)
                    .multiplicativeExpression(0).castExpression(0).unaryExpression().postfixExpression().primaryExpression().getText();

            SymbolTable temp_parent = parent;
            int flag1 = 0;
            int flag2 = 0;
            String feild_type_2 = null;
            if(str2.matches(intRegex)) {
                feild_type_2 = "int";
                flag2 = 1;
            }
            else if(str2.matches(doubleRegex)) {
                feild_type_2 = "double";
                flag2 = 1;
            }
            else if(str2.matches(charRegex)) {
                feild_type_2 = "char";
                flag2 = 1;
            }

            String str3  = "Field_" + str1 ;
            String str4  = "Field_" + str2 ;
            Map.Entry<String, String> entry1 = null;
            Map.Entry<String, String> entry2 = null;

            while(temp_parent.name != "program"){
                for(Map.Entry<String, String> entry : temp_parent.items.entrySet()){
                    if(str3.equals(entry.getKey())){
                        entry1 = entry;
                        flag1 = 1;
                    }
                    if(str4.equals(entry.getKey())){
                        entry2 = entry;
                        int index1 = entry2.getValue().indexOf("type") + 6;
                        int index2 = entry2.getValue().length() - 1;
                        feild_type_2 = entry2.getValue().substring(index1, index2);
                        flag2 = 1;
                    }
                }
                temp_parent = temp_parent.parent;
            }

            if(flag1 == 0 && parent.check_error){
                int line = ctx.getStart().getLine();
                int col = ctx.getStart().getCharPositionInLine();
                System.out.println("Error106 : in line [" + line + ":" + col + "], Can not find Variable " + str1);
            }
            if(flag2 == 0 && parent.check_error){
                int line = ctx.getStart().getLine();
                int col = ctx.getStart().getCharPositionInLine();
                System.out.println("Error106 : in line [" + line + ":" + col + "], Can not find Variable " + str2);
            }

            if (flag1 != 0 && flag2 != 0 ) {
                int index1 = entry1.getValue().indexOf("type") + 6;
                int index2 = entry1.getValue().length() - 1;
                String feild_type_1 = entry1.getValue().substring(index1, index2);

                if (!feild_type_1.equals(feild_type_2) && parent.check_error) {
                    int line = ctx.getStart().getLine();
                    int col = ctx.getStart().getCharPositionInLine();
                    System.out.println("Error 230 : in line ["+line+":"+col+"], Incompatible types : ["+feild_type_1+"]" +
                            " can not be converted to ["+feild_type_2+"]");
                }
            }

        } catch (Exception e) {
        }
    }

    @Override
    public void exitAssignmentExpression(CParser.AssignmentExpressionContext ctx) {

    }

    @Override
    public void enterAssignmentOperator(CParser.AssignmentOperatorContext ctx) {

    }

    @Override
    public void exitAssignmentOperator(CParser.AssignmentOperatorContext ctx) {

    }

    @Override
    public void enterExpression(CParser.ExpressionContext ctx) {

    }

    @Override
    public void exitExpression(CParser.ExpressionContext ctx) {

    }

    @Override
    public void enterConstantExpression(CParser.ConstantExpressionContext ctx) {

    }

    @Override
    public void exitConstantExpression(CParser.ConstantExpressionContext ctx) {

    }

    @Override
    public void enterDeclaration(CParser.DeclarationContext ctx) {

        try {
            String feild_type_1 = ctx.declarationSpecifiers().declarationSpecifier(0).typeSpecifier().getText();
            String str2 = ctx.initDeclaratorList().initDeclarator(0).initializer().assignmentExpression().conditionalExpression().logicalOrExpression()
                    .logicalAndExpression(0).inclusiveOrExpression(0).exclusiveOrExpression(0).andExpression(0).equalityExpression(0)
                    .relationalExpression(0).shiftExpression(0).additiveExpression(0).multiplicativeExpression(0).castExpression(0)
                    .unaryExpression().postfixExpression().primaryExpression().getText();
        SymbolTable temp_parent = parent;
        int flag2 = 0;
        String feild_type_2 = null;
        if(str2.matches(intRegex)) {
            feild_type_2 = "int";
            flag2 = 1;
        }
        else if(str2.matches(doubleRegex)) {
            feild_type_2 = "double";
            flag2 = 1;
        }
        else if(str2.matches(charRegex)) {
            feild_type_2 = "char";
            flag2 = 1;
        }

        String str4  = "Field_" + str2 ;
        Map.Entry<String, String> entry2 = null;

        while(temp_parent.name != "program"){
            for(Map.Entry<String, String> entry : temp_parent.items.entrySet()){
                if(str4.equals(entry.getKey())){
                    entry2 = entry;
                    int index1 = entry2.getValue().indexOf("type") + 6;
                    int index2 = entry2.getValue().length() - 1;
                    feild_type_2 = entry2.getValue().substring(index1, index2);
                    flag2 = 1;
                }
            }
            temp_parent = temp_parent.parent;
        }
        if(flag2 == 0 && parent.check_error){
            int line = ctx.getStart().getLine();
            int col = ctx.getStart().getCharPositionInLine();
            System.out.println("Error106 : in line [" + line + ":" + col + "], Can not find Variable " + str2);
        }
        else {
            if (!feild_type_1.equals(feild_type_2) && parent.check_error) {
                int line = ctx.getStart().getLine();
                int col = ctx.getStart().getCharPositionInLine();
                System.out.println("Error 230 : in line ["+line+":"+col+"], Incompatible types : ["+feild_type_1+"]" +
                        " can not be converted to ["+feild_type_2+"]");
            }
        }

    } catch (Exception e) {
    }

}

    @Override
    public void exitDeclaration(CParser.DeclarationContext ctx) {
        String name = ctx.initDeclaratorList().initDeclarator(0).declarator().
                directDeclarator().Identifier().getText();
        String type = ctx.declarationSpecifiers().declarationSpecifier(0).typeSpecifier().getText();
        String str = "Field_" + name;
        for(Map.Entry<String, String> entry : parent.items.entrySet()){
            if(str.equals(entry.getKey()) && parent.check_error){
                int col = ctx.getStart().getCharPositionInLine();
                System.out.println("Error104 : in line [" + ctx.getStart().getLine() +":" + col + "] , field " + str.substring(6) + " has been defined already\n");
                str = str + "_" + ctx.getStart().getLine() + "_" + col;
            }
        }
        try{
            int size = ctx.initDeclaratorList().initDeclarator(0).initializer().initializerList().initializer().size();
            parent.items.put(str, "methodField(name: " + name + ") (type: " + type + " array, length = "+ size+ ")");
        } catch(Exception e){
            parent.items.put(str, "methodField(name: " + name + ") (type: " + type + ")");
        }
    }

    @Override
    public void enterDeclarationSpecifiers(CParser.DeclarationSpecifiersContext ctx) {

    }

    @Override
    public void exitDeclarationSpecifiers(CParser.DeclarationSpecifiersContext ctx) {

    }

    @Override
    public void enterDeclarationSpecifier(CParser.DeclarationSpecifierContext ctx) {

    }

    @Override
    public void exitDeclarationSpecifier(CParser.DeclarationSpecifierContext ctx) {

    }

    @Override
    public void enterInitDeclaratorList(CParser.InitDeclaratorListContext ctx) {

    }

    @Override
    public void exitInitDeclaratorList(CParser.InitDeclaratorListContext ctx) {

    }

    @Override
    public void enterInitDeclarator(CParser.InitDeclaratorContext ctx) {

    }

    @Override
    public void exitInitDeclarator(CParser.InitDeclaratorContext ctx) {

    }

    @Override
    public void enterStorageClassSpecifier(CParser.StorageClassSpecifierContext ctx) {

    }

    @Override
    public void exitStorageClassSpecifier(CParser.StorageClassSpecifierContext ctx) {

    }

    @Override
    public void enterTypeSpecifier(CParser.TypeSpecifierContext ctx) {

    }

    @Override
    public void exitTypeSpecifier(CParser.TypeSpecifierContext ctx) {

    }

    @Override
    public void enterStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext ctx) {

    }

    @Override
    public void exitStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext ctx) {

    }

    @Override
    public void enterStructOrUnion(CParser.StructOrUnionContext ctx) {

    }

    @Override
    public void exitStructOrUnion(CParser.StructOrUnionContext ctx) {

    }

    @Override
    public void enterStructDeclarationList(CParser.StructDeclarationListContext ctx) {

    }

    @Override
    public void exitStructDeclarationList(CParser.StructDeclarationListContext ctx) {

    }

    @Override
    public void enterStructDeclaration(CParser.StructDeclarationContext ctx) {

    }

    @Override
    public void exitStructDeclaration(CParser.StructDeclarationContext ctx) {

    }

    @Override
    public void enterSpecifierQualifierList(CParser.SpecifierQualifierListContext ctx) {

    }

    @Override
    public void exitSpecifierQualifierList(CParser.SpecifierQualifierListContext ctx) {

    }

    @Override
    public void enterStructDeclaratorList(CParser.StructDeclaratorListContext ctx) {

    }

    @Override
    public void exitStructDeclaratorList(CParser.StructDeclaratorListContext ctx) {

    }

    @Override
    public void enterStructDeclarator(CParser.StructDeclaratorContext ctx) {

    }

    @Override
    public void exitStructDeclarator(CParser.StructDeclaratorContext ctx) {

    }

    @Override
    public void enterEnumSpecifier(CParser.EnumSpecifierContext ctx) {

    }

    @Override
    public void exitEnumSpecifier(CParser.EnumSpecifierContext ctx) {

    }

    @Override
    public void enterEnumeratorList(CParser.EnumeratorListContext ctx) {

    }

    @Override
    public void exitEnumeratorList(CParser.EnumeratorListContext ctx) {

    }

    @Override
    public void enterEnumerator(CParser.EnumeratorContext ctx) {

    }

    @Override
    public void exitEnumerator(CParser.EnumeratorContext ctx) {

    }

    @Override
    public void enterEnumerationConstant(CParser.EnumerationConstantContext ctx) {

    }

    @Override
    public void exitEnumerationConstant(CParser.EnumerationConstantContext ctx) {

    }

    @Override
    public void enterTypeQualifier(CParser.TypeQualifierContext ctx) {

    }

    @Override
    public void exitTypeQualifier(CParser.TypeQualifierContext ctx) {

    }

    @Override
    public void enterDeclarator(CParser.DeclaratorContext ctx) {

    }

    @Override
    public void exitDeclarator(CParser.DeclaratorContext ctx) {

    }

    @Override
    public void enterDirectDeclarator(CParser.DirectDeclaratorContext ctx) {

    }

    @Override
    public void exitDirectDeclarator(CParser.DirectDeclaratorContext ctx) {

    }

    @Override
    public void enterNestedParenthesesBlock(CParser.NestedParenthesesBlockContext ctx) {

    }

    @Override
    public void exitNestedParenthesesBlock(CParser.NestedParenthesesBlockContext ctx) {

    }

    @Override
    public void enterPointer(CParser.PointerContext ctx) {

    }

    @Override
    public void exitPointer(CParser.PointerContext ctx) {

    }

    @Override
    public void enterTypeQualifierList(CParser.TypeQualifierListContext ctx) {

    }

    @Override
    public void exitTypeQualifierList(CParser.TypeQualifierListContext ctx) {

    }

    @Override
    public void enterParameterTypeList(CParser.ParameterTypeListContext ctx) {

    }

    @Override
    public void exitParameterTypeList(CParser.ParameterTypeListContext ctx) {

    }

    @Override
    public void enterParameterList(CParser.ParameterListContext ctx) {

    }

    @Override
    public void exitParameterList(CParser.ParameterListContext ctx) {

    }

    @Override
    public void enterParameterDeclaration(CParser.ParameterDeclarationContext ctx) {

    }

    @Override
    public void exitParameterDeclaration(CParser.ParameterDeclarationContext ctx) {

    }

    @Override
    public void enterIdentifierList(CParser.IdentifierListContext ctx) {

    }

    @Override
    public void exitIdentifierList(CParser.IdentifierListContext ctx) {

    }

    @Override
    public void enterTypeName(CParser.TypeNameContext ctx) {

    }

    @Override
    public void exitTypeName(CParser.TypeNameContext ctx) {

    }

    @Override
    public void enterTypedefName(CParser.TypedefNameContext ctx) {

    }

    @Override
    public void exitTypedefName(CParser.TypedefNameContext ctx) {

    }

    @Override
    public void enterInitializer(CParser.InitializerContext ctx) {

    }

    @Override
    public void exitInitializer(CParser.InitializerContext ctx) {

    }

    @Override
    public void enterInitializerList(CParser.InitializerListContext ctx) {

    }

    @Override
    public void exitInitializerList(CParser.InitializerListContext ctx) {

    }

    @Override
    public void enterDesignation(CParser.DesignationContext ctx) {

    }

    @Override
    public void exitDesignation(CParser.DesignationContext ctx) {

    }

    @Override
    public void enterDesignatorList(CParser.DesignatorListContext ctx) {

    }

    @Override
    public void exitDesignatorList(CParser.DesignatorListContext ctx) {

    }

    @Override
    public void enterDesignator(CParser.DesignatorContext ctx) {

    }

    @Override
    public void exitDesignator(CParser.DesignatorContext ctx) {

    }

    @Override
    public void enterStatement(CParser.StatementContext ctx) {

    }

    @Override
    public void exitStatement(CParser.StatementContext ctx) {

    }

    @Override
    public void enterLabeledStatement(CParser.LabeledStatementContext ctx) {

    }

    @Override
    public void exitLabeledStatement(CParser.LabeledStatementContext ctx) {

    }

    @Override
    public void enterCompoundStatement(CParser.CompoundStatementContext ctx) {

    }

    @Override
    public void exitCompoundStatement(CParser.CompoundStatementContext ctx) {

    }

    @Override
    public void enterBlockItemList(CParser.BlockItemListContext ctx) {

    }

    @Override
    public void exitBlockItemList(CParser.BlockItemListContext ctx) {

    }

    @Override
    public void enterBlockItem(CParser.BlockItemContext ctx) {

    }

    @Override
    public void exitBlockItem(CParser.BlockItemContext ctx) {

    }

    @Override
    public void enterExpressionStatement(CParser.ExpressionStatementContext ctx) {

    }

    @Override
    public void exitExpressionStatement(CParser.ExpressionStatementContext ctx) {

    }

    @Override
    public void enterSelectionStatement(CParser.SelectionStatementContext ctx) {
        String str = ctx.expression().assignmentExpression(0).conditionalExpression().logicalOrExpression()
                .logicalAndExpression(0).inclusiveOrExpression(0).exclusiveOrExpression(0).andExpression(0).equalityExpression(0)
                .relationalExpression(0).shiftExpression(0).additiveExpression(0).multiplicativeExpression(0).castExpression(0)
                .unaryExpression().postfixExpression().primaryExpression().getText();

        SymbolTable temp_parent = parent;
        int flag = 0;
        if(str.matches(intRegex) || str.matches(doubleRegex) || str.matches(charRegex)){
            flag = 1;
        }
        String str1  = "Field_" + str ;
        while(temp_parent.name != "program"){
            for(Map.Entry<String, String> entry : temp_parent.items.entrySet()){
                if(str1.equals(entry.getKey())){
                    flag = 1;
                }
            }
            temp_parent = temp_parent.parent;
        }
        if(flag == 0 && parent.check_error){
            int line = ctx.expression().assignmentExpression(0).conditionalExpression().logicalOrExpression().getStart().getLine();
            int col = ctx.getStart().getCharPositionInLine();
            System.out.println("Error106 : in line [" + line + ":" + col + "], Can not find Variable " + str);
        }
        boolean check_error = true;
        if(nested >= 1){
            if (!parent.check_error){
                check_error = false;
            }
            SymbolTable nested = new SymbolTable(parent,"nested", ctx.getStart().getLine(), null, check_error);
            parent = nested;
        }
        nested++;
    }

    @Override
    public void exitSelectionStatement(CParser.SelectionStatementContext ctx) {
        if(parent.parent != null && nested != 1){
            parent = parent.parent;
        }
        nested--;
    }

    @Override
    public void enterIterationStatement(CParser.IterationStatementContext ctx) {
        boolean check_error = true;
        if(nested >= 1){
            if (!parent.check_error){
                check_error = false;
            }
            SymbolTable nested = new SymbolTable(parent,"nested", ctx.getStart().getLine(), null, check_error);
            parent = nested;
        }
        nested++;
    }

    @Override
    public void exitIterationStatement(CParser.IterationStatementContext ctx) {
        if(parent.parent != null && nested != 1){
            parent = parent.parent;
        }
        nested--;
    }

    @Override
    public void enterForCondition(CParser.ForConditionContext ctx) {

    }

    @Override
    public void exitForCondition(CParser.ForConditionContext ctx) {

    }

    @Override
    public void enterForDeclaration(CParser.ForDeclarationContext ctx) {

    }

    @Override
    public void exitForDeclaration(CParser.ForDeclarationContext ctx) {

    }

    @Override
    public void enterForExpression(CParser.ForExpressionContext ctx) {

    }

    @Override
    public void exitForExpression(CParser.ForExpressionContext ctx) {

    }

    @Override
    public void enterJumpStatement(CParser.JumpStatementContext ctx) {

    }

    @Override
    public void exitJumpStatement(CParser.JumpStatementContext ctx) {

    }

    @Override
    public void enterExternalDeclaration(CParser.ExternalDeclarationContext ctx) {
        SymbolTable program = new SymbolTable(null,"program", ctx.getStart().getLine(), null, true);
        parent = program;
    }

    @Override
    public void exitExternalDeclaration(CParser.ExternalDeclarationContext ctx) {
        parent.print();
    }
    int index = 0;
    @Override
    public void enterFunctionDefinition(CParser.FunctionDefinitionContext ctx){
        SymbolTable func;
        String str = "Method_" + ctx.declarator().directDeclarator().directDeclarator().getText();
        boolean check_error = true;

        String type = ctx.typeSpecifier().getText();
        if(str.equals("Method_main")){
            func = new SymbolTable(parent,"main", ctx.getStart().getLine(), "int", check_error);
            parent.items.put("Method_main", "Method : (name : main) (return type: int)");
            parent = func;
        }else{
            String param = " (parameter list: ";
            if(ctx.declarator().directDeclarator().parameterTypeList() != null){
                for(int i = 0; i < ctx.declarator().directDeclarator().parameterTypeList().parameterList().parameterDeclaration().size(); i++ ){
                    String str1 =  "[name: " + ctx.declarator().directDeclarator().parameterTypeList().parameterList().parameterDeclaration(i)
                            .declarator().directDeclarator().Identifier() +
                            ", type: " + ctx.declarator().directDeclarator().parameterTypeList().parameterList()
                            .parameterDeclaration(i).declarationSpecifiers().declarationSpecifier(0).typeSpecifier().getText() +
                            ", index: " + i + "]";
                    param = param + str1;
                    if(i != ctx.declarator().directDeclarator().parameterTypeList().parameterList().parameterDeclaration().size() -1){
                        param = param + ", ";
                    }
                }
            }
            param = param + ")";
            //check double definition of a method
            for(Map.Entry<String, String> entry : parent.items.entrySet()){
                if(str.equals(entry.getKey())){
                    int col = ctx.getStart().getCharPositionInLine();
                    System.out.println("Error102 : in line [" + ctx.getStart().getLine() +":" + col + "] , method " + str.substring(7) + " has been defined already\n");
                    str = str + "_" + ctx.getStart().getLine() + "_" + col;
                    check_error = false;
                }
            }
            func = new SymbolTable(parent, str, ctx.getStart().getLine(), type, check_error);
            parent.items.put( str, "Method : (name : " + str +") (return type: "+ type
                    + ")" + param);
            parent = func;
            if(ctx.declarator().directDeclarator().parameterTypeList() != null){
                for(int i = 0; i < ctx.declarator().directDeclarator().parameterTypeList().parameterList().parameterDeclaration().size(); i++ ){
                    String name = ctx.declarator().directDeclarator().parameterTypeList().parameterList().parameterDeclaration(i)
                            .declarator().directDeclarator().Identifier().getText();
                    String field_type = ctx.declarator().directDeclarator().parameterTypeList().parameterList()
                            .parameterDeclaration(i).declarationSpecifiers().declarationSpecifier(0).typeSpecifier().getText();
                    try{

                        String size =ctx.declarator().directDeclarator().parameterTypeList().parameterList().parameterDeclaration(i).declarator()
                                .directDeclarator().Constant(0).getText();
                        parent.items.put("Field_" + name, "methodParamField(name: " + name + ") (type: " + field_type + " array, length = " + size + ")");
                    } catch(Exception e){
                        parent.items.put("Field_" + name, "methodParamField(name: " + name + ") (type: " + field_type + ")");
                    }
                }
            }
        }
    }

    @Override
    public void exitFunctionDefinition(CParser.FunctionDefinitionContext ctx) {
        // check return type
        String return_type = parent.return_type;
        int index = 0;
        try {
            index = ctx.compoundStatement().blockItemList().blockItem().size() - 1;
            String str = ctx.compoundStatement().blockItemList().blockItem(index).statement().jumpStatement().getText();
            String returned = str.substring(6, str.length()-1);
            if(return_type.equals("void")){
                if(returned.length()!=0 && parent.check_error){
                    int line = ctx.compoundStatement().blockItemList().blockItem(index).getStart().getLine();
                    int col = ctx.getStart().getCharPositionInLine();
                    System.out.println("Error210 : in line ["+line+":" + col + "], ReturnType of this method must be "+return_type);
                }
            }else{
                String feild_type = "";
                if(returned.matches(intRegex)) {
                    feild_type = "int";
                }else if (returned.matches(doubleRegex)){
                    feild_type = "double";
                }else if (returned.matches(charRegex)) {
                    feild_type = "char";
                }else{
                    for (Map.Entry<String, String> entry : parent.items.entrySet()) {
                        if (returned.equals(entry.getKey().substring(6))) {
                            int index1 = entry.getValue().indexOf("type") + 6;
                            int index2 = entry.getValue().length() - 1;
                            feild_type = entry.getValue().substring(index1, index2);
                        }
                    }
                }
                if(feild_type == "" && parent.check_error){
                    int line = ctx.compoundStatement().blockItemList().blockItem(index).getStart().getLine();
                    int col = ctx.getStart().getCharPositionInLine();
                    System.out.println("Error106 : in line [" + line + ":" + col + "], Can not find Variable " + returned);
                }
                if (!feild_type.equals(return_type) && parent.check_error) {
                    int line = ctx.compoundStatement().blockItemList().blockItem(index).getStart().getLine();
                    int col = ctx.getStart().getCharPositionInLine();
                    System.out.println("Error210 : in line [" + line + ":"+col+"], ReturnType of this method must be " + return_type);
                }
            }
        } catch (Exception e) {
            if(!return_type.equals("void") && parent.check_error){
                int line = ctx.getStart().getLine();
                int col = ctx.getStart().getCharPositionInLine();
                System.out.println("Error210 : in line ["+line+":"+col+"], ReturnType of this method must be "+return_type);
            }
        }
        if(parent.parent != null){
            parent = parent.parent;
        }
}
    @Override
    public void enterDeclarationList(CParser.DeclarationListContext ctx) {

    }

    @Override
    public void exitDeclarationList(CParser.DeclarationListContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode node) {

    }

    @Override
    public void visitErrorNode(ErrorNode node) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {

    }
}
