package test.java;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ListTest {

    private List<Object> listToTest;
    private List<Integer> integerListToTest;
    private Class listImplementation;

    private Object sampleObject1;
    private Object sampleObject2;
    private Object sampleObject3;
    private Object[] objectArray;


    private ListTest(List<Object> listToTest) {
        listImplementation = listToTest.getClass();
    }

    public static ListTest linkedList() {
        return new ListTest(new LinkedList<>());
    }

    public static ListTest arrayList() {
        return new ListTest(new ArrayList<>());
    }

    public static void main(String[] args) {

        ListTest linkedListTests = ListTest.linkedList();
        ListTest arrayListListTests = ListTest.arrayList();

        linkedListTests.callAllTestMethods();
        arrayListListTests.callAllTestMethods();

    }

    private void setUpNewEmptyList() {

        try {
            listToTest = (List<Object>) listImplementation.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setUpNewEmptyIntegerList() {

        try {
            integerListToTest = (List<Integer>) listImplementation.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void cleanAfterAssignNullToList() {
        setUpNewEmptyList();
    }

    private void createSampleObjects() {
        sampleObject1 = new Object();
        sampleObject2 = new Object();
        sampleObject3 = new Object();
    }

    private void createArrayOfSampleObject(int numberOfObject) {
        objectArray = new Object[numberOfObject];
        for (int i = 0; i < numberOfObject; i++) {
            objectArray[i] = new Object();
        }
    }

    private void setUpListWithThreeObjectElements() {
        setUpNewEmptyList();
        createSampleObjects();
        listToTest.add(sampleObject1);
        listToTest.add(sampleObject2);
        listToTest.add(sampleObject3);
    }

    private int getRandomInt(int min, int max) {
        int randomInt = (int) (Math.random() * (max - min) + min);
        return randomInt;
    }

    @Test(name = "size()")
    public void afterCreateNewList_listSizeEqualsZero() {
        // given
        setUpNewEmptyList();

        // when

        // then
        assert 0 == listToTest.size() : "incorrect list size";
    }

    @Test(name = "size()")
    public void afterAddThreeElementsToList_listSizeEqualsThree() {
        // given
        setUpNewEmptyList();
        createSampleObjects();

        // when
        listToTest.add(sampleObject1);
        listToTest.add(sampleObject1);
        listToTest.add(sampleObject1);

        //then
        assert 3 == listToTest.size() : "incorrect list size";
    }

    @Test(name = "add()")
    public void afterAddRandomNumberElementsToList_listSizeEqualsThisRandomNumber() {
        // given
        setUpNewEmptyList();
        int randomInt = getRandomInt(1, 100);
        createArrayOfSampleObject(randomInt);

        // when
        for (Object object : objectArray) {
            listToTest.add(object);
        }

        //then
        assert randomInt == listToTest.size() : "incorrect list size";
    }

    @Test(name = "add()")
    public void afterAddTheSameObjectToList_theListSizeIncearesByOne() {
        // given
        setUpNewEmptyList();
        createSampleObjects();
        listToTest.add(sampleObject1);
        listToTest.add(sampleObject2);
        listToTest.add(sampleObject3);

        // when
        listToTest.add(sampleObject3);

        // then
        int correctSize = 4;
        assert listToTest.size() == correctSize : "object not in list";
    }

    @Test(name = "addAll()")
    public void afterAddAllRandomNumberElementsToList_listSizeEqualsThisRandomNumber() {
        // given
        setUpNewEmptyList();
        int randomInt = getRandomInt(1, 100);
        createArrayOfSampleObject(randomInt);

        // when
        listToTest.addAll(Arrays.asList(objectArray));

        //then
        assert randomInt == listToTest.size() : "incorrect list size";
    }

    @Test(name = "isEmpty()")
    public void ifListIsNull_afterCallingIsEmpty_NullPointerExceptionIsThrown() {
        // given
        setUpNewEmptyList();
        listToTest = null;

        // when
        boolean isNPE = false;
        try {
            listToTest.isEmpty();
        } catch (NullPointerException e) {
            isNPE = true;
        }

        // then
        assert isNPE : "NullPointerException hasn't thrown";
        cleanAfterAssignNullToList();
    }

    @Test(name = "isEmpty()")
    public void afterCreateNewList_isEmptyIsTrue() {
        // given
        setUpNewEmptyList();

        // when

        // then
        assert listToTest.isEmpty() : "list should be empty";
    }

    @Test(name = "isEmpty()")
    public void afterAddObjectToList_isEmptyIsFalse() {
        // given
        setUpNewEmptyList();

        // when
        listToTest.add(new Object());

        // then
        assert !listToTest.isEmpty() : "list shouldn't be empty";
    }

    @Test(name = "add(), contains()")
    public void afterAddObjectToList_theListContainsTheObject() {
        // given
        setUpNewEmptyList();

        // when
        Object objectToAdd = new Object();
        listToTest.add(objectToAdd);

        // then
        assert listToTest.contains(objectToAdd) : "object not in list";
    }

    @Test(name = "contains()")
    public void objectThatWasntAddedToList_shouldntBeInEmptyList() {
        // given
        setUpNewEmptyList();
        Object objectToAdd = new Object();

        // when

        // then
        assert !listToTest.contains(objectToAdd) : "object shouldn't be in list";
    }

    @Test(name = "listIterator()")
    public void listIteratorOnNonEmptyList_ReturnsListIteratorObject() {
        // given
        setUpListWithThreeObjectElements();

        // when

        // then
        assert listToTest.listIterator() instanceof ListIterator : "";
    }

    @Test(name = "listIterator()")
    public void listIteratorOnEmptyList_ReturnsListIteratorObject() {
        // given
        setUpNewEmptyList();

        // when


        // then
        assert Arrays.asList(listToTest.listIterator().getClass().getInterfaces()).contains(ListIterator.class)  : "";
    }

    @Test(name = "listIterator()")
    public void listIteratorOnThreeObjectList_hasThreeNexts() {
        // given
        setUpListWithThreeObjectElements();

        // when
        ListIterator li = listToTest.listIterator();
        int i = 0;
        while (li.hasNext()) {
            li.next();
            i++;
        }

        // then
        assert i == 3  : "";
    }

    @Test(name = "remove(int)")
    public void afterRemoveElementByIndex_listSizeDecreasedByOne() {
        // given
        setUpListWithThreeObjectElements();
        // when
        listToTest.remove(1);
        // then
        assert listToTest.size() == 2 : "";
    }

    @Test(name = "remove(int)")
    public void afterRemoveElementByIndex_listDoesntContainsThisElement() {
        // given
        setUpListWithThreeObjectElements();
        // when
        listToTest.remove(1);
        // then
        assert !listToTest.contains(sampleObject2) : "";
    }

    @Test(name = "remove(int)")
    public void afterRemoveElementByNegativeIndex_IndexOutOfBoundsExceptionIsThrown() {
        // given
        setUpListWithThreeObjectElements();
        // when
        boolean isIOOE = false;
        try {
            listToTest.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            isIOOE = true;
        }
        // then
        assert isIOOE : "IndexOutOfBoundsException hasn't thrown if index in remove method is negative";
    }

    @Test(name = "remove(int)")
    public void afterRemoveElementByIndexBiggerThanTheLastIndex_IndexOutOfBoundsExceptionIsThrown() {
        // given
        setUpListWithThreeObjectElements();
        // when
        boolean isIOOE = false;
        try {
            listToTest.remove(3);
        } catch (IndexOutOfBoundsException e) {
            isIOOE = true;
        }
        // then
        assert isIOOE : "IndexOutOfBoundsException hasn't thrown if index in remove method bigger than the last index of list";
    }

    @Test(name = "set(int, E)")
    public void afterSetElement_thisElementIsInList() {
        // given
        setUpListWithThreeObjectElements();
        Object newObject = new Object();
        // when
        listToTest.set(1, newObject);
        // then
        assert listToTest.contains(newObject): "list doesn't contain object to set";
    }


    @Test(name = "set(int, E)")
    public void afterSetElement_substitutedElementNotInList() {
        // given
        setUpListWithThreeObjectElements();
        Object newObject = new Object();
        // when
        listToTest.set(1, newObject);
        // then
        assert !listToTest.contains(sampleObject2): "list contains object that shouldn't be there";
    }

    @Test(name = "set(int, E)")
    public void afterSetElement_listSizeShouldBeTheSame() {
        // given
        setUpListWithThreeObjectElements();
        Object newObject = new Object();
        // when
        listToTest.set(1, newObject);
        // then
        assert listToTest.size() == 3: "list size increased or decreased";
    }

    @Test(name = "remove(int)")
    public void afterRemoveElementByIndex_removedObjectIsReturned() {
        // given
        setUpListWithThreeObjectElements();
        // when
        Object removedObject = listToTest.remove(1);
        // then
        assert removedObject.equals(sampleObject2) : "Wrong object has been returned";
    }

    @Test(name = "remove(int)")
    public void afterRemoveElementByIndex_indexOfObjectAfterTheRemovedObjectIsDecreasedByOne() {
        // given
        setUpListWithThreeObjectElements();
        // when
        listToTest.remove(1);
        // then
        assert listToTest.indexOf(sampleObject3) == 1 : "Incorrect index of object after removed object or object not in list";
    }

    @Test(name = "remove(Object)")
    public void afterRemoveElementByObject_listSizeDecreasedByOne() {
        // given
        setUpListWithThreeObjectElements();
        // when
        listToTest.remove(sampleObject1);
        // then
        assert listToTest.size() == 2 : "List size hasn't been decreased by one";
    }

    @Test(name = "remove(Object)")
    public void afterRemoveElementByObject_listDoesntContainsThisElement() {
        // given
        setUpListWithThreeObjectElements();
        // when
        listToTest.remove(sampleObject2);
        // then
        assert !listToTest.contains(sampleObject2) : "List contains object that should be removed";
    }

    @Test(name = "remove(Object)")
    public void afterTryToRemoveElementThatNotInList_listIsUnchanged() {
        // given
        setUpListWithThreeObjectElements();
        // when
        Object objectThatIsNotInList = new Object();
        List<Object> listCopy = null;
        try {
            listCopy = (List<Object>) listImplementation.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        listCopy.add(sampleObject1);
        listCopy.add(sampleObject2);
        listCopy.add(sampleObject3);

        Collections.copy(listCopy, listToTest);
        listToTest.remove(objectThatIsNotInList);
        // then

        assert listCopy.equals(listToTest) : "list after try to remove object that is not in list has been changed";

    }


    @Test(name = "remove(Object)")
    public void afterRemoveElementByObject_trueIsReturnedIfObjectWasInList() {
        // given
        setUpListWithThreeObjectElements();
        // when
        boolean isRemoved = listToTest.remove(sampleObject1);
        // then
        assert isRemoved : "Expected true, false returned";
    }

    @Test(name = "remove(Object)")
    public void afterRemoveElementByObject_indexOfObjectAfterTheRemovedObjectIsDecreasedByOne() {
        // given
        setUpListWithThreeObjectElements();
        // when
        listToTest.remove(sampleObject2);
        // then
        assert listToTest.indexOf(sampleObject3) == 1 : "Incorrect index of object after removed object or object not in list";
    }

    public void callAllTestMethods() {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (isTestMethod(m)) {
                try {
                    m.invoke(this);
                    System.out.format("OK %s %s passed\n", listToTest.getClass().getSimpleName(), m.getName());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    if (e.getCause().getClass().equals(AssertionError.class))
                        System.out.format("BAD %s %s FAILED %s\n", listToTest.getClass().getSimpleName(), m.getName(), e.getCause().getMessage());
                    else {
                        System.out.format("Another Exception %s %s %s\n", listToTest.getClass().getSimpleName(), m.getName(), e.getCause());
                    }
                }
            }
        }
    }

    private boolean isTestMethod(Method method) {
        boolean isTest = false;
        Annotation[] annotations = method.getDeclaredAnnotations();
        if (annotations.length == 0)
            return false;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Test.class)) {
                isTest = true;
                break;
            }
        }
        return isTest;
    }
}
